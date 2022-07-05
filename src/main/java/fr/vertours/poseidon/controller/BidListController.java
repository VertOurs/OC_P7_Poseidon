package fr.vertours.poseidon.controller;


import fr.vertours.poseidon.converter.ConverterEntityToDTO;
import fr.vertours.poseidon.domain.BidList;
import fr.vertours.poseidon.dto.BidListDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.service.IBidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Controller
@Slf4j
public class BidListController {

    private final IBidListService service;

    public BidListController(IBidListService service) {
        this.service = service;
    }


    //http://localhost:8080/bidList/list
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("list", service.findAll());
        return "bidList/list";
    }
    //http://localhost:8080/bidList/add
    @GetMapping("/bidList/add")
    public String addBidForm(BidListDTO dto) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListDTO dto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.error("field Validation errors");
            return "bidList/add";
        }
            service.save(dto);

        model.addAttribute("list", service.findAll());
        return "bidList/list";
    }



    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList;
        try {
             bidList = service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        model.addAttribute("bidListDTO", ConverterEntityToDTO.getBidListDTO(bidList));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDTO dto,
                            BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.error("field Validation errors");
            return "bidList/update";
        }
        service.updateId(id, dto);
        return "redirect:/bidList/list";
    }



    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        service.deleteId(id);

        return "redirect:/bidList/list";
    }
}

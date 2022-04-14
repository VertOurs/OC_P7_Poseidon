package fr.vertours.poseidon.controller;



import fr.vertours.poseidon.dto.CurvePointDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.service.ICurveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class CurveController {

    private final ICurveService service;

    public CurveController(ICurveService service) {
        this.service = service;
    }

    //http://localhost:8080/curvePoint/list
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("list", service.findAll());
        return "curvePoint/list";
    }

    //http://localhost:8080/curvePoint/add
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePointDTO dto) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointDTO dto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.error("field Validation errors");
            return "curvePoint/add";
        }
        service.save(dto);
        model.addAttribute("list", service.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model,
                                 CurvePointDTO dto) {
        try {
            service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePointDTO dto,
                            BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.error("field Validation errors");
            return "curvePoint/update";
        }
        try {
            service.updateId(id, dto);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        service.deleteId(id);
        return "redirect:/curvePoint/list";
    }
}

package fr.vertours.poseidon.controller;

import fr.vertours.poseidon.converter.ConverterEntityToDTO;
import fr.vertours.poseidon.domain.Trade;
import fr.vertours.poseidon.dto.TradeDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.service.ITradeService;
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
public class TradeController {

    private final ITradeService service;

    public TradeController(ITradeService service) {
        this.service = service;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("list", service.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(TradeDTO dto) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid TradeDTO dto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.error("field Validation errors");
            return "trade/add";
        }
        service.save(dto);
        model.addAttribute("list", service.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade;
        try {
            trade = service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        model.addAttribute("tradeDTO", ConverterEntityToDTO.getTradeDTO(trade));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDTO dto,
                              BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.error("field Validation errors");
            return "trade/update";
        }
        service.updateId(id, dto);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        try {
            service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        service.deleteId(id);
        return "redirect:/trade/list";
    }
}

package fr.vertours.poseidon.controller;

import fr.vertours.poseidon.dto.RatingDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.service.IRatingService;
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
public class RatingController {

    private final IRatingService service;

    public RatingController(IRatingService service) {
        this.service = service;
    }

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("list", service.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(RatingDTO dto) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid RatingDTO dto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.error("field Validation errors");
            return "rating/add";
        }
        service.save(dto);
        model.addAttribute("list", service.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RatingDTO dto) {
        try {
            service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDTO dto,
                               BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.error("field Validation errors");
            return "rating/update";
        }
        service.updateId(id, dto);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        try {
            service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        service.deleteId(id);
        return "redirect:/rating/list";
    }
}

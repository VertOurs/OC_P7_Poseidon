package fr.vertours.poseidon.controller;

import fr.vertours.poseidon.domain.RuleName;
import fr.vertours.poseidon.dto.RuleNameDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.service.IRuleNameService;
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
public class RuleNameController {

    private final IRuleNameService service;

    public RuleNameController(IRuleNameService service) {
        this.service = service;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("list", service.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleNameDTO dto) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleNameDTO dto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.error("field Validation errors");
            return "ruleName/add";
        }
        service.save(dto);
        model.addAttribute("list", service.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model,
                                 RuleNameDTO dto) {
        try {
            service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id,
                                 @Valid RuleNameDTO dto,
                                 BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.error("field Validation errors");
            return "ruleName/update";
        }
        service.updateId(id, dto);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        try {
            service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        service.deleteId(id);
        return "redirect:/ruleName/list";
    }
}

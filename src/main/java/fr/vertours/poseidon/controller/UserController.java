package fr.vertours.poseidon.controller;

import fr.vertours.poseidon.converter.ConverterEntityToDTO;
import fr.vertours.poseidon.domain.User;
import fr.vertours.poseidon.dto.UserDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.repository.UserRepository;
import fr.vertours.poseidon.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserController {

    private final UserRepository userRepository;
    private final IUserService service;

    public UserController(UserRepository userRepository, IUserService service) {
        this.userRepository = userRepository;
        this.service = service;
    }

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", service.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUserForm(UserDTO dto) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid UserDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("field Validation errors");
            return "user/add";
        }
        service.save(dto);
        model.addAttribute("users", service.findAll());
        return "/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user;
        try {
            user = service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        model.addAttribute("userDTO", ConverterEntityToDTO.getUserDTO(user));
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDTO dto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("field Validation errors");
            return "user/update";
        }
        try {
            service.updateId(id, dto);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        try {
            service.findId(id);
        } catch (InvalidIDException e) {
            log.error("Error message: "+ e.getMessage()
                    + "  StackTrace: " + e.getStackTrace());
            return "404";
        }
        service.deleteId(id);
        return "redirect:/user/list";
    }
}

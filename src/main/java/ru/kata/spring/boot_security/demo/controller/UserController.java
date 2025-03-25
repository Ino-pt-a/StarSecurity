package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.sevice.RoleService;
import ru.kata.spring.boot_security.demo.sevice.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Return your login template
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }


    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") @Valid User user,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        Role userRole = roleService.findByName("ROLE_USER");

        if (userRole == null) {
            throw new IllegalStateException("ROLE_USER is not found in the database!");
        }

        user.setRoles(Set.of(userRole));
        userService.save(user);
        return "redirect:/login?registered";
    }


    @GetMapping("/user")

    public String userPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "user";
    }
}
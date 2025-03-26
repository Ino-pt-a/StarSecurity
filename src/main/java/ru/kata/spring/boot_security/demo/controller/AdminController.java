package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@Transactional
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/add";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("user") User user,
                          @RequestParam("roles") List<Long> roleIds,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "admin/add";
        }

        Set<Role> selectedRoles = new HashSet<>();
        for (Long roleId : roleIds) {
            selectedRoles.add(roleService.findById(roleId));
        }
        user.setRoles(selectedRoles);

        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/edit";
    }

    @PostMapping("/edit")
    public String updateUser(@Valid @ModelAttribute("user") User user,
                             @RequestParam("roles") List<Long> roleIds,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "admin/edit";
        }

        Set<Role> selectedRoles = new HashSet<>();
        for (Long roleId : roleIds) {
            selectedRoles.add(roleService.findById(roleId));
        }
        user.setRoles(selectedRoles);

        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}

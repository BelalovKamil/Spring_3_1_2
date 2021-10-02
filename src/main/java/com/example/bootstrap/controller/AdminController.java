package com.example.bootstrap.controller;

import com.example.bootstrap.model.Role;
import com.example.bootstrap.model.User;
import com.example.bootstrap.service.RoleService;
import com.example.bootstrap.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("authorizedUser", user);
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRole());
        return "admin";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "role-checkbox") String[] roleCheckbox) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : roleCheckbox) {
            roleSet.add(roleService.getRoleByName(roles));
        }
        user.setRoleSet(roleSet);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/users/remove/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("updateUser") User user
            , @PathVariable("id") Long id
            , @RequestParam(value = "role-checkbox") String[] roleCheckbox) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : roleCheckbox) {
            roleSet.add(roleService.getRoleByName(roles));
        }
        user.setRoleSet(roleSet);
        userService.updateUserById(id, user);
        return "redirect:/admin";
    }
}

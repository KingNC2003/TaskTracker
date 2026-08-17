package com.project.tasktracker.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final InMemoryUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            InMemoryUserDetailsManager userDetailsManager,
            PasswordEncoder passwordEncoder) {

        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        if (username.isBlank() || password.isBlank()) {
            model.addAttribute(
                    "error",
                    "Username and password are required."
            );
            return "register";
        }

        if (userDetailsManager.userExists(username)) {
            model.addAttribute(
                    "error",
                    "Username already exists."
            );
            return "register";
        }
        
        UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build();
        userDetailsManager.createUser(user);
        return "redirect:/login?registered";
    }
}
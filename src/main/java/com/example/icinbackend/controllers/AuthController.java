package com.example.icinbackend.controllers;

import com.example.icinbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/health")
    public String checkHealth() {
        return "ok";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/login")
    public void login() {
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    public void createUser(@RequestBody Map<String, String> user) {
        System.out.println("fml");
        String username = user.get("username");
        String password = user.get("password");
        if (username.isEmpty() || password.isEmpty()) {
            throw new InvalidParameterException();
        }
        this.userService.addUser(user.get("username"), user.get("password"));
    }
}

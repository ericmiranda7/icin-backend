package com.example.icinbackend.DAO.controllers;

import com.example.icinbackend.ApiResponse;
import com.example.icinbackend.security.JWTUtil;
import com.example.icinbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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
    public ApiResponse createUser(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = passwordEncoder.encode(user.get("password"));
        if (username.isEmpty() || password.isEmpty()) {
            throw new InvalidParameterException();
        }
        this.userService.addUser(user.get("username"), user.get("password"));
        return new ApiResponse(HttpStatus.OK.value(), "Token generated", jwtUtil.generateToken(username));
    }
}

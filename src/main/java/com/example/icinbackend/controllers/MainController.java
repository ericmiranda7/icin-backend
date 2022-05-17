package com.example.icinbackend.controllers;

import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/api")
public class MainController {
    @Operation(summary = "Get all the products in stock", description = "Get a list of all the products in stock and filter by search criteria if provided.")
    @GetMapping("/health")
    public String healthCheck() {
        return "All good";
    }

    @GetMapping("/secHealth")
    public String secHealth() {
        return "You can reach this";
    }

}

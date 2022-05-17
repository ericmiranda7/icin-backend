package com.example.icinbackend.controllers;

import com.example.icinbackend.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidParameterException;


@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public String userNotFound() {
        System.out.println("HeEYY");
        return "no user";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ApiResponse> badRegistrationData(InvalidParameterException ex, WebRequest request) {
        System.out.println("Send proper params");
        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Username or password is blank");
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }
}

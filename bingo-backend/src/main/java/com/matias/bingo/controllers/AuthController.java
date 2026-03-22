package com.matias.bingo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matias.bingo.dtos.Response;
import com.matias.bingo.dtos.auth.LoginRequestDTO;
import com.matias.bingo.dtos.auth.RefreshTokenRequestDTO;
import com.matias.bingo.dtos.users.UserRegistrationDTO;
import com.matias.bingo.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody UserRegistrationDTO dto) {
        Response response = userService.register(dto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequestDTO dto) {
        Response response = userService.login(dto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Response> refreshToken(@RequestBody RefreshTokenRequestDTO dto) {
        Response response = userService.refreshToken(dto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}

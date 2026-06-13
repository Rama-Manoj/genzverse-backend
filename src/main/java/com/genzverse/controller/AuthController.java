package com.genzverse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genzverse.dto.ForgotPasswordRequest;
import com.genzverse.dto.LoginRequest;
import com.genzverse.dto.LoginResponse;
import com.genzverse.dto.RegisterRequest;
import com.genzverse.dto.ResetPasswordRequest;
import com.genzverse.dto.VerifyEmailRequest;
import com.genzverse.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
    private final AuthService authService;

    public AuthController( AuthService authService)
    {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(
           @Valid @RequestBody RegisterRequest request)
    {
        String response = authService.register(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request)
    {
        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String>
    forgotPassword(
            @RequestBody
            ForgotPasswordRequest request)
    {
        return ResponseEntity.ok(
                authService.forgotPassword(request)
        );
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<String>
    resetPassword(
            @RequestBody
            ResetPasswordRequest request)
    {
        return ResponseEntity.ok(
                authService.resetPassword(request)
        );
    }
    
    
    @GetMapping("/verify-email")
    public String verifyEmail( @RequestParam String token)
    {
        VerifyEmailRequest request = new VerifyEmailRequest();

        request.setToken(token);

        return authService.verifyEmail(request);
    }
    
}

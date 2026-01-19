package com.TaskFlow.TaskFlow.controller;

import com.TaskFlow.TaskFlow.dto.request.LoginRequest;
import com.TaskFlow.TaskFlow.dto.request.RegisterRequest;
import com.TaskFlow.TaskFlow.dto.response.AuthResponse;
import com.TaskFlow.TaskFlow.entity.User;
import com.TaskFlow.TaskFlow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth") 
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")   
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request ){
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @PostMapping("/register")   
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        return new ResponseEntity<>(authService.registerUser(request), HttpStatus.CREATED);
    }



    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new AuthResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail()
        ));
    }
}
package com.TaskFlow.TaskFlow.service;

import org.springframework.stereotype.Service;

import com.TaskFlow.TaskFlow.dto.request.LoginRequest;
import com.TaskFlow.TaskFlow.dto.request.RegisterRequest;
import com.TaskFlow.TaskFlow.dto.response.AuthResponse;
import com.TaskFlow.TaskFlow.entity.User;
import com.TaskFlow.TaskFlow.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
    
    private final UserRepository userRepository;

    public AuthService(UserRepository UserRepository) {
        this.userRepository = UserRepository;
    }
    
    @Transactional
    public AuthResponse registerUser(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está en uso");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());

        //encriptar contraseña con bcrypt 
        user.setPassword(registerRequest.getPassword());

        userRepository.save(user);
        return new AuthResponse(user.getUsername(), user.getEmail());
    }

    public AuthResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(()-> new RuntimeException("Credenciales inválidas"));

        if(!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }


        return new AuthResponse(user.getUsername(), user.getEmail());
    }
}

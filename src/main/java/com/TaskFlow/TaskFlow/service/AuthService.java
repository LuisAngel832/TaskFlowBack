package com.TaskFlow.TaskFlow.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TaskFlow.TaskFlow.dto.request.LoginRequest;
import com.TaskFlow.TaskFlow.dto.request.RegisterRequest;
import com.TaskFlow.TaskFlow.dto.response.AuthResponse;
import com.TaskFlow.TaskFlow.entity.Role;
import com.TaskFlow.TaskFlow.entity.User;
import com.TaskFlow.TaskFlow.exception.BadCredentialsException;
import com.TaskFlow.TaskFlow.exception.EmailAlreadyExistsException;
import com.TaskFlow.TaskFlow.repository.UserRepository;
import com.TaskFlow.TaskFlow.security.JwtService;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * POSIBLES MEJORAS:
     * - Enviar email de verificación
     * - Validar formato de email con regex
     * - Validar fortaleza de contraseña
     * - Implementar rate limiting para evitar spam de registros
     */
    @Transactional
    public AuthResponse registerUser(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("El correo electrónico ya está en uso");
        }

        User user = new User();
        user.setUsername(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());

        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));

        user.setRoles(Set.of(Role.USER));

        userRepository.save(user);

        return new AuthResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    public AuthResponse loginUser(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(user.getId(), user.getUsername(), user.getEmail(), token);
    }
}

package com.TaskFlow.TaskFlow.controller;

import com.TaskFlow.TaskFlow.dto.request.LoginRequest;
import com.TaskFlow.TaskFlow.dto.request.RegisterRequest;
import com.TaskFlow.TaskFlow.dto.response.AuthResponse;
import com.TaskFlow.TaskFlow.entity.User;
import com.TaskFlow.TaskFlow.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticación", description = "Operaciones relacionadas con la autenticación de usuarios")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Logear a un usuario registrado", description = """
            Retorna informacion del usuario junto con un token JWT al iniciar sesión correctamente.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @Operation(summary = "Registrar un usuario nuevo", description = """
            Crea un nuevo usuario en el sistema y retorna su información junto con un token JWT.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o correo ya en uso")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authService.registerUser(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener información del usuario autenticado", description = "Retorna la información del usuario actualmente autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Información del usuario obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado")

    })
    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()));
    }
}
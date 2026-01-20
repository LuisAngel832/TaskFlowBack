package com.TaskFlow.TaskFlow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Solicitud de inicio de sesión")
public class LoginRequest {

    @Email(message = "Debe ser un correo electrónico válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Schema(description = "Correo electrónico del usuario", example = "usuario@example.com")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Schema(description = "Contraseña del usuario", example = "password123")
    private String password;
}

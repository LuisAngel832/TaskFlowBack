package com.TaskFlow.TaskFlow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Solicitud de registro de nuevo usuario")
public class RegisterRequest {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String name;

    @Email(message = "Debe ser un correo electrónico válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Schema(description = "Correo electrónico del usuario", example = "juan@example.com")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Schema(description = "Contraseña del usuario (mínimo 8 caracteres)", example = "password123")
    private String password;
}

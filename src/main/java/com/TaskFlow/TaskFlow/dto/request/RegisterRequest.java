package com.TaskFlow.TaskFlow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;
    @Email(message = "Debe ser un correo electrónico válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}

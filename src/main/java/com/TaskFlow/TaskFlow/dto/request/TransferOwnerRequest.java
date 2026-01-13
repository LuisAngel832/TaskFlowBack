package com.TaskFlow.TaskFlow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferOwnerRequest {
    @NotNull(message = "El id del proyecto no puede esta vacio")
    private Long projectId;
    @Email
    @NotBlank(message = "El email del Administrador no puede estar vacio")
    private String currentOwnerEmail;
    @Email
    @NotBlank(message = "El email del nuevo Administrador no puede estar vacio")
    private String newOwnerEmail;
}

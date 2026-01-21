package com.TaskFlow.TaskFlow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando se intenta registrar un email que ya existe.
 * Retorna un HTTP 409 Conflict.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}

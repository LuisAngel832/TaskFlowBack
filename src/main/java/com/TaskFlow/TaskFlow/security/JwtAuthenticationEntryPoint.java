package com.TaskFlow.TaskFlow.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

   
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);


        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "No autorizado");
        body.put("message", determineErrorMessage(request, authException));
        body.put("path", request.getRequestURI());
        body.put("timestamp", LocalDateTime.now().toString());


        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

    private String determineErrorMessage(HttpServletRequest request, AuthenticationException ex) {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || authHeader.isEmpty()) {
            return "Token de autenticación no proporcionado";
        }
        
        if (!authHeader.startsWith("Bearer ")) {
            return "Formato de token inválido. Use: Bearer <token>";
        }
        
        String exMessage = ex.getMessage();
        if (exMessage != null && exMessage.toLowerCase().contains("expired")) {
            return "El token ha expirado. Por favor, inicie sesión nuevamente";
        }
        
        return "Token de autenticación inválido";
    }
}

package com.TaskFlow.TaskFlow.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorableImpl implements  AuditorAware<String>{

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder
        .getContext()
        .getAuthentication();

        if(auth == null || !auth.isAuthenticated()){
            return Optional.empty();
        }

        return Optional.ofNullable(auth.getName());
    }
    
}

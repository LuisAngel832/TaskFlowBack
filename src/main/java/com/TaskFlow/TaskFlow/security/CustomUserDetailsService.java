package com.TaskFlow.TaskFlow.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.TaskFlow.TaskFlow.entity.User;
import com.TaskFlow.TaskFlow.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
 
   
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return user;
    }
}
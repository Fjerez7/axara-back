package com.axara.backend.config;

import com.axara.backend.models.Role;
import com.axara.backend.models.User;
import com.axara.backend.repositories.UserRepository;
import com.axara.backend.services.JwtService;
import com.axara.backend.wire.AuthResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialSetup {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostConstruct
    public AuthResponse init(){
        var userAdmin = User.builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin123"))
                .firstName("admin")
                .lastName("AD")
                .role(Role.ADMIN)
                .build();
        userRepository.save(userAdmin);
        return AuthResponse.builder()
                .token(jwtService.generateToken(userAdmin))
                .build();
    }
}

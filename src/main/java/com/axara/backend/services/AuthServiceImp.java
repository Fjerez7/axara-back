package com.axara.backend.services;

import com.axara.backend.models.Role;
import com.axara.backend.models.User;
import com.axara.backend.repositories.UserRepository;
import com.axara.backend.wire.AuthResponse;
import com.axara.backend.wire.LoginRequest;
import com.axara.backend.wire.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // Method that handles login requests.
    @Override
    public AuthResponse login(LoginRequest request){
        // Authenticates the user's credentials using the AuthenticationManager
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()));
        // Looks up the user in the DB
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        // Generates a token for the user
        var token = jwtService.generateToken(user);
        var role = user.getRole();
        return AuthResponse.builder()
                .token(token)
                .role(role)
                .build();
    }

    // Method that handles registration requests.
    @Override
    public  AuthResponse register(RegisterRequest request){
        // Creates a new User object with the details provided in the request, encrypts the password.
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.CLIENT)
                .build();
        // Saves the user to the database
        userRepository.save(user);
        // Generates a token for the user
        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}

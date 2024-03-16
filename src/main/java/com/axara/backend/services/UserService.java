package com.axara.backend.services;

import com.axara.backend.repositories.UserRepository;
import com.axara.backend.wire.PasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(Long id, PasswordRequest request) {
        userRepository.findById(id)
                .ifPresent(user -> {
                    if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                         user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                         userRepository.save(user);
                    }else {
                        throw new RuntimeException("Current password is incorrect");
                    }
                });
    }
}

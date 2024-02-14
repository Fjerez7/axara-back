package com.axara.backend.controllers;


import com.axara.backend.adapter.UserAdapter;
import com.axara.backend.models.User;
import com.axara.backend.repositories.UserRepository;
import com.axara.backend.wire.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserAdapter userAdapter;
    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authToken) {
        // Get authenticated User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated user found");
        }
        // Get username from authenticated object
        String username = authentication.getName();
        // Get User on DB
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
        //Map UserEntity on UserDTO
        UserDto userDto = userAdapter.modelToWire(user.get());
        return ResponseEntity.ok(userDto);
    }
}

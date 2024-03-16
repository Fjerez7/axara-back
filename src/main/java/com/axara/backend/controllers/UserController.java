package com.axara.backend.controllers;


import com.axara.backend.adapter.UserAdapter;
import com.axara.backend.models.User;
import com.axara.backend.repositories.UserRepository;
import com.axara.backend.services.UserService;
import com.axara.backend.wire.PasswordRequest;
import com.axara.backend.wire.UserDto;
import com.axara.backend.wire.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserAdapter userAdapter;
    private final UserService userService;
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
        userDto.setRole(user.get().getRole().name());
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<?> updateUserByParams(@PathVariable Long id, @RequestBody UserUpdateRequest request){
        return userRepository.findById(id)
                .map(user -> {
                    if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
                    if (request.getLastName() != null) user.setLastName(request.getLastName());
                    userRepository.save(user);
                    return ResponseEntity.ok(userAdapter.modelToWire(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/user/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody PasswordRequest request){
        userService.changePassword(id, request);
        return ResponseEntity.ok().build();
    }
}

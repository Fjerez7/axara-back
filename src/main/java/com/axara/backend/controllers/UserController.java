package com.axara.backend.controllers;

import com.axara.backend.adapter.UserAdapter;
import com.axara.backend.models.User;
import com.axara.backend.services.UserService;
import com.axara.backend.wire.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class UserController {

    private  UserService userService;
    private  UserAdapter userAdapter;

    @Autowired
    public UserController(UserService userService, UserAdapter userAdapter) {
        this.userService = userService;
        this.userAdapter = userAdapter;
    }

    @GetMapping("/api/users")
    public Stream<UserDto> getAllUsers(){

        return userAdapter.modelStreamToWireStream(userService.getAllUsers().stream());
    }

    @PostMapping("/api/users")
    public UserDto saveUser(@RequestBody UserDto userDto){
        //Transform wire to model
        User user = userAdapter.wireToModel(userDto);
        System.out.println("md + "+ userDto.getFirstName());
        System.out.println("dto " + user.getFirstName());
        // saved data on DB
        User savedUser = userService.saveUser(user);

        return userAdapter.modelToWire(savedUser);
    }
}
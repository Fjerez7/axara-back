package com.axara.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DemoController {

    @GetMapping("/demo/protected")
    public String welcome(){
        return "Welcome from secure endpoint";
    }
    @GetMapping("/demo/public")
    public String welcomePublic(){return "Welcome from public endpoint";}
}

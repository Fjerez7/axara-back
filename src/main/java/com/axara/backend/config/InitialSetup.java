package com.axara.backend.config;

import com.axara.backend.models.Role;
import com.axara.backend.models.User;
import com.axara.backend.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup {

    private final UserRepository userRepository;

    @Autowired
    public InitialSetup(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init(){
        User adminUser = userRepository.findByEmail("admin@userAdmin.com").orElseGet(() -> {
            User newAdminUser = new User();
            newAdminUser.setFirstName("Admin");
            newAdminUser.setLastName("User");
            newAdminUser.setEmail("admin@userAdmin.com");
            newAdminUser.setPassword("admin123");
            newAdminUser.setRole(Role.ADMIN);
            return userRepository.save(newAdminUser);
        });
    }
}

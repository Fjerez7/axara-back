package com.axara.backend.wire;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserProfile {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

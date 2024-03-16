package com.axara.backend.wire;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}

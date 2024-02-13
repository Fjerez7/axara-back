package com.axara.backend.services;

import com.axara.backend.wire.AuthResponse;
import com.axara.backend.wire.LoginRequest;
import com.axara.backend.wire.RegisterRequest;

public interface AuthService {
     AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);

}

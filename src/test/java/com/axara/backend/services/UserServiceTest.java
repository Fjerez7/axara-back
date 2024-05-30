package com.axara.backend.services;

import com.axara.backend.models.User;
import com.axara.backend.repositories.UserRepository;
import com.axara.backend.wire.PasswordRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;


@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    public void testChangePassword(){
        Long id = 1L;
        PasswordRequest request = new PasswordRequest();
        request.setCurrentPassword("password");
        request.setNewPassword("newPassword");
        request.setConfirmPassword("newPassword");

        User user = User.builder()
                .id(id).password("password")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(request.getNewPassword())).thenReturn("encodedPassword");

        userService.changePassword(id, request);

        verify(userRepository, times(2)).save(any(User.class));

    }
}

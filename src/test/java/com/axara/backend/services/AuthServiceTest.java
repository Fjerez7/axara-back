package com.axara.backend.services;


import com.axara.backend.models.Role;
import com.axara.backend.models.User;
import com.axara.backend.repositories.UserRepository;
import com.axara.backend.wire.AuthResponse;
import com.axara.backend.wire.LoginRequest;
import com.axara.backend.wire.RegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {

        @Autowired
        private AuthService authService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private UserRepository userRepository;

        @MockBean
        private AuthenticationManager authenticationManager;

        @BeforeEach
        public void setUp() {
            userRepository.deleteAll();
        }

        @Test
        public void testRegister(){
            RegisterRequest request = RegisterRequest.builder()
                    .email("fjerez@gmail.com").firstName("Fabian").lastName("Jerez").password("password123")
                    .build();

            AuthResponse response = authService.register(request);
            Assertions.assertNotNull(response.getToken());

            User foundUser = userRepository.findByEmail(request.getEmail()).orElseThrow();
            Assertions.assertEquals(request.getEmail(), foundUser.getEmail());
            Assertions.assertEquals(request.getFirstName(), foundUser.getFirstName());
            Assertions.assertEquals(request.getLastName(), foundUser.getLastName());
            Assertions.assertTrue(passwordEncoder.matches(request.getPassword(), foundUser.getPassword()));
        }

        @Test
        public void testLogin(){
            String password = passwordEncoder.encode("password123");
            LoginRequest request = LoginRequest.builder()
                    .email("fjerez@gmail.com").password(password).build();
            User user = User.builder()
                    .email("fjerez@gmail.com").password(password).firstName("Fabian")
                    .lastName("Jerez").role(Role.CLIENT)
                    .build();
            userRepository.save(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

            AuthResponse response = authService.login(request);
            System.out.println(response.getToken() + " tokenLogin");
            Assertions.assertNotNull(response.getToken());
            Assertions.assertEquals(user.getRole(), response.getRole());
        }
}

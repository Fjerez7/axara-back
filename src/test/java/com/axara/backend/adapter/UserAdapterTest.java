package com.axara.backend.adapter;

import com.axara.backend.models.Role;
import com.axara.backend.models.User;
import com.axara.backend.wire.UserDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAdapterTest {

    @Autowired
    private UserAdapter userAdapter;

    @Test
    public void testModelToWire() {
        User user = User.builder()
            .id(1L).firstName("Fabian").lastName("Jerez").email("f@gmail.com").password("1234").role(Role.CLIENT)
                .build();

        UserDto userDto = userAdapter.modelToWire(user);

        UserDto expectedUserDto = UserDto.builder()
            .id(1L).firstName("Fabian").lastName("Jerez").email("f@gmail.com").password("1234").role("CLIENT")
                .build();
        assertEquals(expectedUserDto, userDto);
    }
}

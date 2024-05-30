package com.axara.backend.services;

import com.axara.backend.models.Cart;
import com.axara.backend.models.User;
import com.axara.backend.repositories.CartRepository;
import com.axara.backend.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;
    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void testCreateCart(){
        Long userId = 1L;
        User user = User.builder().id(userId).firstName("John").lastName("Doe").email("jDoe@gmail.com").password("1234")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Cart cart = Cart.builder().id(1L).user(user).build();
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.createCart(userId);
        Assertions.assertNotNull(result.getId());
    }

    @Test
    public void testGetCart(){
        Long cartId = 1L;
        Cart cart = Cart.builder().id(cartId).orderItems(new ArrayList<>()).build();
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        Cart result = cartService.getCart(cartId);
        Assertions.assertNotNull(result);
    }

}

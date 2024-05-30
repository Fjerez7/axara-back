package com.axara.backend.services;

import com.axara.backend.models.Cart;
import com.axara.backend.models.OrderItem;
import com.axara.backend.models.Product;
import com.axara.backend.repositories.CartRepository;
import com.axara.backend.repositories.OrderItemRepository;
import com.axara.backend.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderItemServiceTest {
    @Autowired
    private OrderItemService orderItemService;
    @MockBean
    private OrderItemRepository orderItemRepository;
    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testAddToCart(){
        Long cartId = 1L;
        Long productId = 1L;
        OrderItem orderItem = OrderItem.builder().id(1L).build();
        Cart cart = Cart.builder().id(cartId).orderItems(new ArrayList<>()).build();
        Product product = Product.builder().id(productId).build();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        OrderItem result = orderItemService.addToCart(cartId, productId);

        verify(cartRepository, times(1)).findById(cartId);
        verify(productRepository, times(1)).findById(productId);
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
        Assertions.assertNotNull(result);
    }
}

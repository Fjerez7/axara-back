package com.axara.backend.adapter;

import com.axara.backend.models.*;
import com.axara.backend.wire.CartDto;
import com.axara.backend.wire.OrderItemDto;
import com.axara.backend.wire.ProductDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CartAdapterTest {

    @Autowired
    private CartAdapter cartAdapter;

    @Test
    public void testModelToWire () {
        User userForCart = User.builder()
                .id(1L).firstName("Fabian").lastName("Jerez").email("f@gmail.com").role(Role.CLIENT).password("1234")
                .build();

        Product productForCart = Product.builder()
                .id(1L).name("Product").description("Description").price(BigDecimal.valueOf(10.000)).stock(10).size("S")
                .category("Category").images(new ArrayList<>()).build();

        OrderItem orderItemForCart = OrderItem.builder()
                .id(1L).quantity(1).price(BigDecimal.valueOf(10.000)).product(productForCart).build();

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItemForCart);
        Cart cart = Cart.builder()
                .id(1L)
                .user(userForCart)
                .orderItems(orderItems)
                .totalAmount(BigDecimal.valueOf(10.000))
                .build();

        CartDto cartDto = cartAdapter.modelToWire(cart);

        ProductDto productDto = ProductDto.builder()
                .id(1L).name("Product").description("Description").price(BigDecimal.valueOf(10.000)).stock(10).size("S")
                .category("Category").images(new ArrayList<>()).build();
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .id(1L).quantity(1).price(BigDecimal.valueOf(10.000)).product(productDto).build();
        List<OrderItemDto> orderItemsDto = new ArrayList<>();
        orderItemsDto.add(orderItemDto);

        CartDto expectedCartDto = CartDto.builder()
                .id(1L)
                .userId(1L)
                .orderItems(orderItemsDto)
                .totalAmount(BigDecimal.valueOf(10.000))
                .build();

        assertEquals(expectedCartDto, cartDto);
    }

}

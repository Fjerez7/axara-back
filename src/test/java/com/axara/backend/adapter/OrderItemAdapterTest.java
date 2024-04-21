package com.axara.backend.adapter;



import com.axara.backend.models.Cart;
import com.axara.backend.models.OrderItem;
import com.axara.backend.models.Product;
import com.axara.backend.models.User;
import com.axara.backend.wire.OrderItemDto;
import com.axara.backend.wire.ProductDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderItemAdapterTest {

    @Autowired
    private OrderItemAdapter orderItemAdapter;

    @Test
    public void testModelToWire(){
        Cart cart = Cart.builder()
                .id(1L)
                .build();
        Product product = Product.builder()
                .id(1L)
                .name("Product")
                .price(BigDecimal.valueOf(10.000))
                .build();
        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .cart(cart)
                .product(product)
                .quantity(1)
                .price(BigDecimal.valueOf(10.000))
                .build();

        OrderItemDto orderItemDto = orderItemAdapter.modelToWire(orderItem);

        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("Product")
                .price(BigDecimal.valueOf(10.000))
                .build();
        OrderItemDto expectedOrderItemDto = OrderItemDto.builder()
                .id(1L)
                .product(productDto)
                .quantity(1)
                .price(BigDecimal.valueOf(10.000))
                .build();

        assertEquals(expectedOrderItemDto, orderItemDto);
    }

}

package com.axara.backend.services;

import com.axara.backend.models.Cart;
import com.axara.backend.models.OrderItem;
import com.axara.backend.models.Product;
import com.axara.backend.repositories.CartRepository;
import com.axara.backend.repositories.OrderItemRepository;
import com.axara.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderItem addToCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        for(OrderItem orderItem : cart.getOrderItems()) {
            if (orderItem.getProduct().getId().equals(productId)) {
                orderItem.setQuantity(orderItem.getQuantity() + 1);
                orderItem.setPrice(orderItem.getPrice().add(product.getPrice()));
                return orderItemRepository.save(orderItem);
            }
        }
        OrderItem orderItem = OrderItem.builder()
                .cart(cart)
                .product(product)
                .quantity(1)
                .price(product.getPrice())
                .build();
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public void deleteOrderItemFromCart(Long id) {
        orderItemRepository.deleteById(id);
    }
}

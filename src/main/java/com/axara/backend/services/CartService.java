package com.axara.backend.services;

import com.axara.backend.models.Cart;
import com.axara.backend.models.OrderItem;
import com.axara.backend.repositories.CartRepository;
import com.axara.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Transactional
    public Cart createCart(Long userId) {
        var user = userRepository.findById(userId).orElseThrow();
        Cart cart = Cart.builder()
                .user(user)
                .build();
        return   cartRepository.save(cart);
    }

    @Transactional
    public Cart getCart(Long id) {
        Cart cartFinded = cartRepository.findById(id).orElseThrow();
        BigDecimal total = cartFinded.getOrderItems().stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cartFinded.setTotalAmount(total);
        return cartFinded;
    }
}

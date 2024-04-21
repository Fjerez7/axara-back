package com.axara.backend.controllers;

import com.axara.backend.adapter.CartAdapter;
import com.axara.backend.models.Cart;
import com.axara.backend.services.CartService;
import com.axara.backend.wire.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartAdapter cartAdapter;

    @PostMapping
    public ResponseEntity<?> createCart(@RequestParam Long userId){
        Cart cart = cartService.createCart(userId);
        CartDto cartDto = cartAdapter.modelToWire(cart);
        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCart(@PathVariable Long id){
        Cart cart = cartService.getCart(id);
        CartDto cartDto = cartAdapter.modelToWire(cart);
        return ResponseEntity.ok(cartDto);
    }

}

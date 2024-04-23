package com.axara.backend.controllers;


import com.axara.backend.adapter.CheckoutAdapter;
import com.axara.backend.models.Address;
import com.axara.backend.models.Cart;
import com.axara.backend.models.Checkout;
import com.axara.backend.services.CheckoutService;
import com.axara.backend.wire.CheckoutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final CheckoutAdapter checkoutAdapter;

    @PostMapping("/{cartId}")
    public ResponseEntity<CheckoutDto> createCheckout(@PathVariable Long cartId, @RequestBody Address address){
        Checkout checkout = checkoutService.createCheckout(cartId, address);
        CheckoutDto checkoutDto = checkoutAdapter.modelToWire(checkout);
        return ResponseEntity.ok(checkoutDto);
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeCheckout(@RequestBody Cart cart){
        var orderItems = cart.getOrderItems();
        checkoutService.completeCheckout(orderItems);
        return ResponseEntity.ok("Checkout completed");
    }
}

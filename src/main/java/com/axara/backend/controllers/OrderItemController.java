package com.axara.backend.controllers;

import com.axara.backend.adapter.OrderItemAdapter;
import com.axara.backend.models.OrderItem;
import com.axara.backend.services.OrderItemService;
import com.axara.backend.wire.OrderItemDeleteRes;
import com.axara.backend.wire.OrderItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderItemAdapter orderItemAdapter;

    @PostMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId) {
        OrderItem newOrderItem = orderItemService.addToCart(cartId,productId);
        OrderItemDto orderItemDto = orderItemAdapter.modelToWire(newOrderItem);
        return ResponseEntity.ok(orderItemDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderItemDeleteRes> deleteOrderItemFromCart(@PathVariable Long id) {
        orderItemService.deleteOrderItemFromCart(id);
        OrderItemDeleteRes response = OrderItemDeleteRes.builder()
                .message("Order item deleted")
                .id(id)
                .build();
        return ResponseEntity.ok(response);
    }
}

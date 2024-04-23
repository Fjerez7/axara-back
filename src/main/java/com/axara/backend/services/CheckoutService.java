package com.axara.backend.services;

import com.axara.backend.models.Address;
import com.axara.backend.models.Cart;
import com.axara.backend.models.Checkout;
import com.axara.backend.models.OrderItem;
import com.axara.backend.repositories.AddressRepository;
import com.axara.backend.repositories.CartRepository;
import com.axara.backend.repositories.CheckoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartRepository cartRepository;
    private final CheckoutRepository checkoutRepository;
    private final AddressRepository addressRepository;
    private final ProductService productService;


    public Checkout createCheckout(Long cartId, Address address){
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        Address addressSaved = Address.builder()
                .address(address.getAddress())
                .city(address.getCity())
                .departament(address.getDepartament())
                .postalCode(address.getPostalCode())
                .phone(address.getPhone())
                .build();
        addressRepository.save(addressSaved);
        Checkout checkout = Checkout.builder()
                .cart(cart)
                .address(addressSaved)
                .build();
        return checkoutRepository.save(checkout);
    }

    public void completeCheckout(List<OrderItem> orderItems){
        for (OrderItem orderItem : orderItems) {
            productService.updateProductStock(orderItem.getProduct().getId(), orderItem.getQuantity());
        }
    }
}

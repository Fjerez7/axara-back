package com.axara.backend.services;

import com.axara.backend.models.Address;
import com.axara.backend.models.Cart;
import com.axara.backend.models.Checkout;
import com.axara.backend.repositories.AddressRepository;
import com.axara.backend.repositories.CartRepository;
import com.axara.backend.repositories.CheckoutRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CheckoutServiceTest {
    @Autowired
    private CheckoutService checkoutService;
    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private CheckoutRepository checkoutRepository;
    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private ProductService productService;

    @Test
    public void testCreateCheckout() {
        Long cartId = 1L;
        Cart cart = Cart.builder().id(cartId).user(null).build();
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        Address address = Address.builder()
                .address("address").city("city").departament("departament").postalCode("postalCode").phone("phone")
                .build();
        when(addressRepository.save(address)).thenReturn(address);

        when(checkoutRepository.save(any(Checkout.class))).thenAnswer(invocation -> {
            Checkout checkout = invocation.getArgument(0);
            checkout.setId(1L);
            return checkout;
        });
        Checkout result = checkoutService.createCheckout(cartId, address);

        verify(cartRepository, times(1)).findById(cartId);
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(checkoutRepository, times(1)).save(any(Checkout.class));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(cart, result.getCart());
        Assertions.assertEquals(address, result.getAddress());
    }
}

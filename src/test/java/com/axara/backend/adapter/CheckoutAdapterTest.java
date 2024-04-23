package com.axara.backend.adapter;

import com.axara.backend.models.Address;
import com.axara.backend.models.Cart;
import com.axara.backend.models.Checkout;
import com.axara.backend.wire.AddressDto;
import com.axara.backend.wire.CheckoutDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

@SpringBootTest
public class CheckoutAdapterTest {
    @Autowired
    private CheckoutAdapter checkoutAdapter;

    @Test
    public void testModelToWire(){
        Cart cart = Cart.builder()
                .id(1L).totalAmount(BigDecimal.valueOf(10.000)).build();
        Address address = Address.builder()
                .id(1L).address("Cra 34 # 32 - 65").city("Bucaramanga").departament("Santander").postalCode("680001")
                .phone("3016667436").build();
        Checkout checkout = Checkout.builder()
                .id(1L)
                .cart(cart)
                .address(address)
                .build();

        CheckoutDto checkoutDto = checkoutAdapter.modelToWire(checkout);

        AddressDto addressDto = AddressDto.builder()
                .id(1L).address("Cra 34 # 32 - 65").city("Bucaramanga").departament("Santander").postalCode("680001")
                .phone("3016667436").build();
        CheckoutDto expectedCheckoutDto = CheckoutDto.builder()
                .id(1L).cartId(1L).address(addressDto).build();
        assertEquals(expectedCheckoutDto, checkoutDto);
    }
}

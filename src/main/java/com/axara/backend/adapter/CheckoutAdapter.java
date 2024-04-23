package com.axara.backend.adapter;

import com.axara.backend.models.Checkout;
import com.axara.backend.wire.CheckoutDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckoutAdapter {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "address", source = "address")
    CheckoutDto modelToWire(Checkout checkout);
}

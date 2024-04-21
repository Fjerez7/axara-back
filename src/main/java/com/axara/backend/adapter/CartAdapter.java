package com.axara.backend.adapter;

import com.axara.backend.models.Cart;
import com.axara.backend.wire.CartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CartAdapter {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "orderItems")
    @Mapping(source = "totalAmount", target = "totalAmount")
    CartDto modelToWire(Cart cart);
}

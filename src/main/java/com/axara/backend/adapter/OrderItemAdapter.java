package com.axara.backend.adapter;

import com.axara.backend.models.OrderItem;
import com.axara.backend.wire.OrderItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemAdapter {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "product", target = "product")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    OrderItemDto modelToWire(OrderItem orderItem);
}

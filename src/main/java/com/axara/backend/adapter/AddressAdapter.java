package com.axara.backend.adapter;

import com.axara.backend.models.Address;
import com.axara.backend.wire.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressAdapter {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "departament", source = "departament")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "phone", source = "phone")
    AddressDto modelToWire(Address address);
}

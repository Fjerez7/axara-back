package com.axara.backend.adapter;

import com.axara.backend.models.User;
import com.axara.backend.wire.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface UserAdapter {


    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    UserDto modelToWire(User user);

//    @Mapping(source = "firstName", target = "firstName")
//    @Mapping(source = "lastName", target = "lastName")
//    @Mapping(source = "email", target = "email")
//    @Mapping(source = "password", target = "password")
    User wireToModel(UserDto userDto);


    Stream<UserDto> modelStreamToWireStream (Stream<com.axara.backend.models.User> user);
    Stream<User> wireStreamToModelStream (Stream<UserDto> user);
}

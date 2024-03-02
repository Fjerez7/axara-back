package com.axara.backend.adapter;

import com.axara.backend.models.Product;
import com.axara.backend.wire.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface ProductAdapter {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "stock", target = "stock")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "images", target = "images")
    ProductDto modelToWire(Product product);

    Product wireToModel(ProductDto productDto);

    Stream<ProductDto> modelStreamToWireStream (Stream<Product> productStream);

}

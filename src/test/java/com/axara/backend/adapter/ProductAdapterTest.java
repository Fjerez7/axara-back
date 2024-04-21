package com.axara.backend.adapter;

import com.axara.backend.models.Product;
import com.axara.backend.wire.ProductDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootTest
public class ProductAdapterTest {

    @Autowired
    private ProductAdapter productAdapter;

    @Test
    public void testModelToWire() {
        Product product = Product.builder()
            .id(1L).name("Product").description("Description").price(BigDecimal.valueOf(10.000)).size("M").stock(10)
            .category("Category").images(new ArrayList<>())
            .build();

        ProductDto productDto = productAdapter.modelToWire(product);

        ProductDto expectedProductDto = ProductDto.builder()
            .id(1L).name("Product").description("Description").price(BigDecimal.valueOf(10.000)).size("M").stock(10)
            .category("Category").images(new ArrayList<>())
            .build();
        assertEquals(expectedProductDto, productDto);
    }
}

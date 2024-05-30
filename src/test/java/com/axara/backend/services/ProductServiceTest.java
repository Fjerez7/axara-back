package com.axara.backend.services;

import com.axara.backend.models.Product;
import com.axara.backend.repositories.ProductRepository;
import com.axara.backend.wire.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct(){

        Product product = Product.builder()
                .id(1L).name("T-shirt").description("T-shirt description").price(BigDecimal.valueOf(10000))
                .size("M").stock(10).category("CLOTHES").images(new ArrayList<>())
                .build();
        when(productRepository.save(product)).thenReturn(product);

        ProductDto productDto = ProductDto.builder()
                .id(1L).name("T-shirt").description("T-shirt description").price(BigDecimal.valueOf(10000))
                .size("M").stock(10).build();

        Product result = productService.saveProduct(productDto);

        assertEquals(product, result);
    }
    @Test
    public void testUpdateProduct(){
        Product product = Product.builder()
                .id(1L).name("T-shirt").description("T-shirt description").price(BigDecimal.valueOf(10000))
                .size("M").stock(10).category("CLOTHES").images(new ArrayList<>())
                .build();
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.updateProduct(product);

        assertEquals(product, result);
    }
    @Test
    public void testGetAllProducts(){
        List<Product> products = new ArrayList<>();
        Product product = Product.builder()
                .id(1L).name("T-shirt").description("T-shirt description").price(BigDecimal.valueOf(10000))
                .size("M").stock(10).category("CLOTHES").images(new ArrayList<>())
                .build();
        products.add(product);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();
        assertEquals(products, result);
    }
    @Test
    public void testGetProductById(){
        Long id = 1L;
        Product product = Product.builder()
                .id(1L).name("T-shirt").description("T-shirt description").price(BigDecimal.valueOf(10000))
                .size("M").stock(10).category("CLOTHES").images(new ArrayList<>())
                .build();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(id);
        assertEquals(Optional.of(product), result);
    }
    @Test
    public void testUpdateProductById(){
        Long id = 1L;
        Product product = Product.builder()
                .id(1L).name("T-shirt").description("T-shirt description").price(BigDecimal.valueOf(10000))
                .size("M").stock(10).category("CLOTHES").images(new ArrayList<>())
                .build();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        ProductDto productDto = ProductDto.builder()
                .name("T-shirt").description("T-shirt description").price(BigDecimal.valueOf(10000))
                .size("M").stock(10).build();

        Product result = productService.updateProductById(id, productDto);
        assertEquals(product, result);
    }
    @Test
    public void testDeleteProductById(){
        Long id = 1L;
        productService.deleteProductById(id);
    }
    @Test
    public void testUpdateProductStock(){
        Long id = 1L;
        Integer quantitySold = 1;
        Product product = Product.builder()
                .id(1L).name("T-shirt").description("T-shirt description").price(BigDecimal.valueOf(10000))
                .size("M").stock(10).category("CLOTHES").images(new ArrayList<>())
                .build();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        productService.updateProductStock(id, quantitySold);
    }
}

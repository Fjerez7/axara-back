package com.axara.backend.services;


import com.axara.backend.models.Product;
import com.axara.backend.repositories.ProductRepository;
import com.axara.backend.wire.ProductDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product saveProduct(ProductDto productDto){
        Product product = Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .size(productDto.getSize())
                .stock(productDto.getStock())
                .category("CLOTHES")
                .images(new ArrayList<>())
                .build();

        return productRepository.save(product);
    }
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById (Long id) {
        return  productRepository.findById(id);
    }

    public Product updateProductById(Long id, ProductDto productDto){
        // Find product for update
        Optional<Product> productForUpdate = productRepository.findById(id);
        // Check if product exist
        if (productForUpdate.isPresent()){
            //Reference to old Product
            Product existingProduct = productForUpdate.get();
            // New Product already updated
            Product updatedProduct = Product.builder()
                    .id(existingProduct.getId())
                    .name(productDto.getName() != null ? productDto.getName() : existingProduct.getName())
                    .description(productDto.getDescription() != null ? productDto.getDescription() : existingProduct.getDescription())
                    .price(productDto.getPrice() != null ? productDto.getPrice() : existingProduct.getPrice())
                    .size(productDto.getSize() != null ? productDto.getSize() : existingProduct.getSize())
                    .stock(productDto.getStock() != null ? productDto.getStock() : existingProduct.getStock())
                    .category(existingProduct.getCategory())
                    .images(existingProduct.getImages())
                    .build();

            // Save new product
            return productRepository.save(updatedProduct);
        }else {
            throw  new EntityNotFoundException("Product with ID: " + id + " not found");
        }
    }
    public void deleteProductById (Long id){
        productRepository.deleteById(id);
    }

}

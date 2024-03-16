package com.axara.backend.controllers;

import com.axara.backend.adapter.ProductAdapter;
import com.axara.backend.models.Image;
import com.axara.backend.models.Product;
import com.axara.backend.repositories.ProductRepository;
import com.axara.backend.services.ProductImageService;
import com.axara.backend.services.ProductService;
import com.axara.backend.wire.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductAdapter productAdapter;
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts (){
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productsDto = productAdapter.modelStreamToWireStream(products.stream()).toList();
        return ResponseEntity.ok(productsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductDto>> getProduct (@PathVariable Long id){
        Optional<Product> productFound = productService.getProductById(id);
        if (productFound.isPresent()){
            ProductDto productDto = productAdapter.modelToWire(productFound.get());
            return ResponseEntity.ok(Optional.ofNullable(productDto));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    //Here
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable Long id, @RequestBody ProductDto productDto){
        Product saveUpdateProduct = productService.updateProductById(id, productDto);
        ProductDto convertUpdateProduct = productAdapter.modelToWire(saveUpdateProduct);
        return ResponseEntity.ok(convertUpdateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        Product product = productRepository.findById(id).orElseThrow();
        if (product != null){
           productImageService.deleteProductImages(product);
            productService.deleteProductById(id);
            return ResponseEntity.ok("Product with ID: " + id + " was deleted");
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(MultipartFile[] files, ProductDto productDto) throws IOException {
        List<Image> imageValues = new ArrayList<>();
        // Definir la ruta de la carpeta de imágenes
        String uploadDir = "src/main/resources/product-images/";

        for (MultipartFile file : files) {
            UUID uuid = UUID.randomUUID();
            // Guardar la imagen en la carpeta de imágenes
            String fileNameForResponse = StringUtils.cleanPath(file.getOriginalFilename());
            String fileNameForFolder = uuid.toString();
            Path path = Paths.get(uploadDir + fileNameForFolder);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            // Agregar el valores de la imagen a la lista
            Image image = new Image(uuid.toString(),fileNameForResponse);
            imageValues.add(image);
        }
        Product saveProductOnDb = productService.saveProduct(productDto);
        saveProductOnDb.setImages(imageValues);
        productService.updateProduct(saveProductOnDb);
        ProductDto productDTO = productAdapter.modelToWire(saveProductOnDb);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }
}

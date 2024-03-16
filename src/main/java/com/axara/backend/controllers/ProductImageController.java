package com.axara.backend.controllers;

import com.axara.backend.repositories.ProductRepository;
import com.axara.backend.services.ProductImageService;
import com.axara.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/products/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/{fileName}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getProductImage(@PathVariable String fileName) throws FileNotFoundException {
        // Construir ruta de la imagen
        String uploadDir = "src/main/resources/product-images/" + fileName;
        var path = new FileInputStream(uploadDir);
        return ResponseEntity.ok(new InputStreamResource(path));
    }

    //Here
    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProductImages(@PathVariable Long productId, MultipartFile[] files) throws IOException {
        productImageService.uploadImage(productId, files);
        return ResponseEntity.ok("Imagen actualizada");
    }

    @DeleteMapping("/{productId}/{imageUid}")
    public ResponseEntity<String> deleteProductImage(@PathVariable Long productId, @PathVariable String imageUid){
        productImageService.deleteImage(productId,imageUid);
        return ResponseEntity.ok("Imagen eliminada");
    }
}

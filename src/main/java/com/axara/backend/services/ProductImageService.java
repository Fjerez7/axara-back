package com.axara.backend.services;

import com.axara.backend.models.Image;
import com.axara.backend.models.Product;
import com.axara.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductRepository productRepository;
    private final ProductService productService;

    //Here
    public void uploadImage(Long productId, MultipartFile[] files) throws IOException {
        List<Image> images = new ArrayList<>();
        String uploadDir = "src/main/resources/product-images/";
        var product = productRepository.findById(productId).orElseThrow();
        for (MultipartFile file : files){
            UUID uuid = UUID.randomUUID();
            String fileNamePath = StringUtils.cleanPath(file.getOriginalFilename());
            String fileNameUuid = uuid.toString();
            Path path = Paths.get(uploadDir + fileNameUuid);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            Image image = new Image(fileNameUuid, fileNamePath);
            images.add(image);
        }
        product.getImages().addAll(images);
        productService.updateProduct(product);
    }

    public void deleteProductImages (Product product){
        String uploadDir = "src/main/resources/product-images/";
        for (Image image : product.getImages()) {
            String imagePath = uploadDir + image.getUid();
            File file = new File(imagePath);
            file.delete();
        }
    }

    //Here
    public void deleteImage(Long productId,String uid){
        String uploadDir = "src/main/resources/product-images/";
        String imagePath = uploadDir + uid;
        var product = productRepository.findById(productId).orElseThrow();
        File file = new File(imagePath);
        file.delete();
        product.getImages().removeIf(image -> image.getUid().equals(uid));
        productService.updateProduct(product);
    }
}

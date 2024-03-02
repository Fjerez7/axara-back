package com.axara.backend.wire;

import com.axara.backend.models.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String size;
    private Integer stock;
    private String category;
    private List<Image> images;
}

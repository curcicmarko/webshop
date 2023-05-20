package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.ProductDto;
import com.example.webshop.model.entity.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product) {

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .availableQuantity(product.getAvailableQuantity())
                .pictureUrl(product.getPictureUrl())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .build();

    }

    public static Product toEntity(ProductDto productDto) {

        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .availableQuantity(productDto.getAvailableQuantity())
                .pictureUrl(productDto.getPictureUrl())
                .build();

    }

}

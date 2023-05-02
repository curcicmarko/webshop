package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.CategoryDto;
import com.example.webshop.model.dto.ProductDto;
import com.example.webshop.model.entity.Category;
import com.example.webshop.model.entity.Product;
import com.example.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductMapper {

    @Autowired
    private ProductRepository productRepository;

    public static ProductDto toDto(Product product) {

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setAvailableQuantity(product.getAvailableQuantity());
        productDto.setPictureUrl(product.getPictureUrl());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryName(product.getCategory().getName());

        return productDto;

    }

    public static Product toEntity(ProductDto productDto) {

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setAvailableQuantity(productDto.getAvailableQuantity());
        product.setPictureUrl(product.getPictureUrl());

        return product;

    }
}

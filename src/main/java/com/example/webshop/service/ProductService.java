package com.example.webshop.service;

import com.example.webshop.model.dto.ProductDto;
import com.example.webshop.model.entity.Category;
import com.example.webshop.model.entity.Product;
import com.example.webshop.model.mapper.ProductMapper;
import com.example.webshop.repository.CategoryRepository;
import com.example.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;


    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return ProductMapper.toDto(product);
    }

    public List<ProductDto> findByName(String name) {
        List<Product> products = productRepository.findByNameContaining(name);
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto createProduct(ProductDto productDto) {

        Product product = new Product();
        product.setName(productDto.getName());
        product.setAvailableQuantity(productDto.getAvailableQuantity());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setPictureUrl(productDto.getPictureUrl());
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category with Id: " + productDto.getCategoryId() + " does not exist"));
        product.setCategory(category);
        productRepository.save(product);

        return ProductMapper.toDto(product);

    }

    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with Id: " + productId + " does not exist"));

        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPictureUrl(productDto.getPictureUrl());
        product.setAvailableQuantity(productDto.getAvailableQuantity());
        product.setCategory(categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category with Id: " + productDto.getCategoryId() + " does not exist")));
        productRepository.save(product);

        return ProductMapper.toDto(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with Id: " + productId + " does not exist"));

        productRepository.delete(product);
    }


}

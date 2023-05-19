package com.example.webshop.service;

import com.example.webshop.exception.NotFoundException;
import com.example.webshop.model.dto.ProductDto;
import com.example.webshop.model.dto.UserDto;
import com.example.webshop.model.entity.Category;
import com.example.webshop.model.entity.Product;
import com.example.webshop.model.entity.User;
import com.example.webshop.model.mapper.ProductMapper;
import com.example.webshop.model.mapper.UserMapper;
import com.example.webshop.repository.CategoryRepository;
import com.example.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<ProductDto> getProductsPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> products = productRepository.findAll(pageable);
        return products.stream()
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
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(String.format
                        ("Category with id %s is not found", productDto.getCategoryId())));

        Product product = Product.builder()
                .name(productDto.getName())
                .availableQuantity(productDto.getAvailableQuantity())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .pictureUrl(productDto.getPictureUrl())
                .category(category)
                .build();

        productRepository.save(product);

        return ProductMapper.toDto(product);
    }

    public ProductDto updateProduct(Long productId, ProductDto productDto) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Product with id %s is not found", productId)));

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id %s is not found", productDto.getCategoryId())));

        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPictureUrl(productDto.getPictureUrl());
        product.setAvailableQuantity(productDto.getAvailableQuantity());
        product.setCategory(category);
        productRepository.save(product);

        return ProductMapper.toDto(product);
    }



    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id %s is not found", productId)));

        productRepository.delete(product);
    }


}

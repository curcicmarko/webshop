package com.example.webshop.model.dto;

import java.util.List;

public class CategoryDto {

    private Long id;
    private String name;
    private List<ProductDto> productDtos;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, List<ProductDto> productDtos) {
        this.id = id;
        this.name = name;
        this.productDtos = productDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductDto> getProductDtos() {
        return productDtos;
    }

    public void setProductDtos(List<ProductDto> productDtos) {
        this.productDtos = productDtos;
    }
}

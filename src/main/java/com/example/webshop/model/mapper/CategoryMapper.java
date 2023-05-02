package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.CategoryDto;
import com.example.webshop.model.entity.Category;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
//        categoryDto.setProductDtos(category.getProducts().stream()
//                .map(ProductMapper::toDto)
//                .collect(Collectors.toList()));

        return categoryDto;
    }

    public static Category toEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
//        category.setProducts(categoryDto.getProductDtos().stream()
//                .map(ProductMapper::toEntity)
//                .collect(Collectors.toList()));

        return category;
    }
}

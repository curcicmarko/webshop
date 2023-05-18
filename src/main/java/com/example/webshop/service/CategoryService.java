package com.example.webshop.service;

import com.example.webshop.model.dto.CategoryDto;
import com.example.webshop.model.entity.Category;
import com.example.webshop.model.mapper.CategoryMapper;
import com.example.webshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id: " + categoryId + " does not exist"));

        return CategoryMapper.toDto(category);
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = new Category();
//        Category ct = Category.builder()
//                .name(categoryDto.getName())
//                .products(List.of())
//                .build();
        category.setName(categoryDto.getName());
        return CategoryMapper.toDto(categoryRepository.save(category));

    }

    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id: " + categoryId + " does not exist"));
        categoryRepository.delete(category);
    }

}

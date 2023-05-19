package com.example.webshop.service;

import com.example.webshop.exception.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %s is not found", categoryId)));
        return CategoryMapper.toDto(category);
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = Category.builder()
                .name(categoryDto.getName())
                .build();

        return CategoryMapper.toDto(categoryRepository.save(category));

    }

    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %s is not found", categoryId)));
        categoryRepository.delete(category);
    }

}

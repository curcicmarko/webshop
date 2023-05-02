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

    public List<CategoryDto> getCategories(){
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCategory(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Category with id: "+id+" dont exist"));

        return CategoryMapper.toDto(category);
    }

    public CategoryDto createCategory(CategoryDto categoryDto){

        Category category = new Category();
        category.setName(categoryDto.getName());

        return CategoryMapper.toDto(categoryRepository.save(category));

    }

}

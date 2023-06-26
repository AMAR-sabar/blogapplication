package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.payload.CategoryDto;
import com.blogapp.repository.CategoryRepo;
import com.blogapp.services.CategoryService;
import lombok.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@Getter
@Setter
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;



    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
       //Category cat= this.modelMapper.map(categoryDto, Category.class);
       Category cat = mapToEntity(categoryDto);
       Category addedCat = this.categoryRepo.save(cat);
        return mapToDto(addedCat);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category= this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updated = this.categoryRepo.save(category);

        return mapToDto(updated);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category= this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        return mapToDto(category);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> cat= categories.stream()
                .map((c)->mapToDto(c))
                .collect(Collectors.toList());
        return cat;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category= this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        this.categoryRepo.delete(category);
    }

    // convert DTO to entity
    private Category mapToEntity(CategoryDto categoryDto)
    {
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        return category;
    }
    //convert Entity to DTO
    private  CategoryDto mapToDto(Category category)
    {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryTitle(category.getCategoryTitle());
        categoryDto.setCategoryDescription(category.getCategoryDescription());
        return categoryDto;
    }


}

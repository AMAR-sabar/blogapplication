package com.blogapp.services;

import com.blogapp.payload.CategoryDto;
import com.blogapp.repository.CategoryRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CategoryService {
    //Create
    public CategoryDto createCategory(CategoryDto categoryDto);
    //Update
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    //Get
    public CategoryDto getCategoryById(Integer categoryId);
    //Get All
    List<CategoryDto> getCategories();
    //Delete
    void deleteCategory(Integer categoryId);
}

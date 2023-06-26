package com.blogapp.controllers;

import com.blogapp.payload.ApiResponse;
import com.blogapp.payload.CategoryDto;
import com.blogapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //Create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
       CategoryDto addCategory = this.categoryService.createCategory(categoryDto);
       return new ResponseEntity<>(addCategory, HttpStatus.CREATED);
    }
    //Update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId)
    {
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }
    //Delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId)
    {
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Deleted successfully",true), HttpStatus.OK);
    }
    //Get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById( @PathVariable Integer categoryId)
    {
        CategoryDto getCategory = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(getCategory,HttpStatus.OK);

    }
    //GetAll
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories()
    {
       List<CategoryDto> getCategory = this.categoryService.getCategories();
        return ResponseEntity.ok(getCategory);
    }
}

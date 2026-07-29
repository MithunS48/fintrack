package com.fintrack.fintrack.controller;

import com.fintrack.fintrack.dto.category.CategoryRequest;
import com.fintrack.fintrack.dto.category.CategoryResponse;
import com.fintrack.fintrack.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request)
    {
        return categoryService.createCategory(request);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategory()
    {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id)
    {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id,@Valid @RequestBody CategoryRequest request)
    {
        return categoryService.updateCategory(id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id)
    {
        categoryService.deleteCategory(id);
    }



}


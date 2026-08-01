package com.fintrack.fintrack.controller;

import com.fintrack.fintrack.dto.category.CategoryRequest;
import com.fintrack.fintrack.dto.category.CategoryResponse;
import com.fintrack.fintrack.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse createCategory(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CategoryRequest request)
    {
        return categoryService.createCategory(userDetails,request);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategory(@AuthenticationPrincipal UserDetails userDetails)
    {
        return categoryService.getAllCategory(userDetails);
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


package com.fintrack.fintrack.controller;


import com.fintrack.fintrack.dto.category.CategoryRequest;
import com.fintrack.fintrack.dto.category.CategoryResponse;
import com.fintrack.fintrack.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminController {

    private final CategoryService categoryService;

    @PostMapping("/category")
    public CategoryResponse createGlobalCategory(
            @Valid @RequestBody CategoryRequest categoryRequest) {

        return categoryService.createGlobalCategory(categoryRequest);
    }

    @PutMapping("/category/{id}")
    public CategoryResponse updateGlobalCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {

        return categoryService.updateGlobalCategory(id, request);
    }

    @DeleteMapping("/category/{id}")
    public void deleteGlobalById(@PathVariable Long id) {
        categoryService.deleteGlobalCategory(id);
    }
}
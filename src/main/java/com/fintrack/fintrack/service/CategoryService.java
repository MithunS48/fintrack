package com.fintrack.fintrack.service;

import com.fintrack.fintrack.dto.category.CategoryRequest;
import com.fintrack.fintrack.dto.category.CategoryResponse;
import com.fintrack.fintrack.entity.Category;
import com.fintrack.fintrack.exception.CategoryAlreadyExistsException;
import com.fintrack.fintrack.exception.CategoryNotFoundException;
import com.fintrack.fintrack.repository.CategoryRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Setter
@Getter
public class CategoryService {

    private final CategoryRepo categoryRepo;


    public CategoryResponse createCategory(CategoryRequest categoryRequest)
    {

        if(categoryRepo.existsByName(categoryRequest.getName()))
        {
            throw new CategoryAlreadyExistsException("category already exist");
        }
        Category category=new Category();

        category.setName(categoryRequest.getName());
        category.setType(categoryRequest.getType());

        Category saveResponse=categoryRepo.save(category);

        CategoryResponse response=new CategoryResponse();

        response.setId(saveResponse.getId());
        response.setName(saveResponse.getName());
        response.setType(saveResponse.getType());


        return response;

    }

    public List<CategoryResponse> getAllCategory()
    {
        List<CategoryResponse> list =new ArrayList<>();

        List<Category> category=categoryRepo.findAll();



        for( Category c:category)
        {
            CategoryResponse response=new CategoryResponse();
            response.setId(c.getId());
            response.setName(c.getName());
            response.setType(c.getType());

            list.add(response);
        }

        return list;


    }

    public CategoryResponse getCategoryById(Long id)
    {
        Category category=categoryRepo.findById(id).orElseThrow(()->new CategoryNotFoundException("Category not found"));
        CategoryResponse response=new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setType(category.getType());

        return response;

    }

    public CategoryResponse updateCategory(Long id,CategoryRequest request)
    {
        Category category=categoryRepo.findById(id).orElseThrow(()->new CategoryNotFoundException("Category not found"));
        category.setName(request.getName());
        category.setType(request.getType());

        Category UCategory =categoryRepo.save(category);

        CategoryResponse response=new CategoryResponse();
        response.setId(UCategory.getId());
        response.setName(UCategory.getName());
        response.setType(UCategory.getType());

        return response;

    }
    public void deleteCategory(Long id)
    {
        Category category=categoryRepo.findById(id).orElseThrow(()->new CategoryNotFoundException("Category not found"));
        categoryRepo.deleteById(category.getId());


    }

}

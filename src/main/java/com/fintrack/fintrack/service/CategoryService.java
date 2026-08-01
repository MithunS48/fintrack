package com.fintrack.fintrack.service;

import com.fintrack.fintrack.dto.category.CategoryRequest;
import com.fintrack.fintrack.dto.category.CategoryResponse;
import com.fintrack.fintrack.entity.Category;
import com.fintrack.fintrack.entity.User;
import com.fintrack.fintrack.exception.CategoryAlreadyExistsException;
import com.fintrack.fintrack.exception.CategoryNotFoundException;
import com.fintrack.fintrack.exception.UserNotFoundException;
import com.fintrack.fintrack.repository.CategoryRepo;
import com.fintrack.fintrack.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Setter
@Getter
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;


    public CategoryResponse createCategory(UserDetails userDetails,CategoryRequest categoryRequest)
    {
        User user = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(categoryRepo.existsByNameAndUser(categoryRequest.getName(),user))
        {
            throw new CategoryAlreadyExistsException("category already exist");
        }
        Category category=new Category();

        category.setName(categoryRequest.getName());
        category.setType(categoryRequest.getType());
        category.setUser(user);


        Category saveResponse=categoryRepo.save(category);

        CategoryResponse response=new CategoryResponse();

        response.setId(saveResponse.getId());
        response.setName(saveResponse.getName());
        response.setType(saveResponse.getType());


        return response;

    }

    public List<CategoryResponse> getAllCategory(UserDetails userDetails)
    {

        List<CategoryResponse> list =new ArrayList<>();
        User user= userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()->new UserNotFoundException("user not found"));

        List<Category> category=categoryRepo.findByUserOrUserIsNull(user);



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




    public CategoryResponse updateCategory(Long id,CategoryRequest request)
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=userRepo.findByEmail(authentication.getName()).orElseThrow(()->new UserNotFoundException("user not found exception"));
        Category category=categoryRepo.findByIdAndUser(id,user).orElseThrow(()->new CategoryAlreadyExistsException("category already exist"));

        if(categoryRepo.existsByNameAndUser(request.getName(), user) && !category.getName().equals(request.getName()))
        {
            throw new CategoryAlreadyExistsException("category already exist");
        }
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
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=userRepo.findByEmail(authentication.getName()).orElseThrow(()->new UserNotFoundException("user not found exception"));
        Category category=categoryRepo.findByIdAndUser(id,user).orElseThrow(()->new CategoryNotFoundException("Category not found"));
        categoryRepo.delete(category);


    }

    public CategoryResponse createGlobalCategory(CategoryRequest categoryRequest)
    {
        if (categoryRepo.existsByNameAndUserIsNull(categoryRequest.getName())) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }
        Category category=new Category();

        category.setName(categoryRequest.getName());
        category.setType(categoryRequest.getType());
        category.setUser(null);


        Category saveResponse=categoryRepo.save(category);

        CategoryResponse response=new CategoryResponse();

        response.setId(saveResponse.getId());
        response.setName(saveResponse.getName());
        response.setType(saveResponse.getType());


        return response;
    }

    public CategoryResponse updateGlobalCategory(Long id,CategoryRequest categoryRequest)
    {
        Category category=categoryRepo.findByIdAndUserIsNull(id).orElseThrow(()->new CategoryNotFoundException("Category not found"));
        if (categoryRepo.existsByNameAndUserIsNull(categoryRequest.getName())
                && !category.getName().equals(categoryRequest.getName())) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }
        category.setName(categoryRequest.getName());
        category.setType(categoryRequest.getType());


        Category saveResponse=categoryRepo.save(category);

        CategoryResponse response=new CategoryResponse();

        response.setId(saveResponse.getId());
        response.setName(saveResponse.getName());
        response.setType(saveResponse.getType());


        return response;

    }


    public void deleteGlobalCategory(Long id)
    {
        Category category=categoryRepo.findByIdAndUserIsNull(id).orElseThrow(()->new CategoryNotFoundException("Category not found"));
        categoryRepo.delete(category);
    }

}

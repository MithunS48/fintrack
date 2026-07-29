package com.fintrack.fintrack.service;

import com.fintrack.fintrack.dto.user.UserRequest;
import com.fintrack.fintrack.dto.user.UserResponse;
import com.fintrack.fintrack.entity.User;
import com.fintrack.fintrack.enums.Role;
import com.fintrack.fintrack.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Setter
@Getter
public class UserService {

    private final UserRepo userRepo;

    public UserResponse createUser(UserRequest request)
    {
        User user =new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);
        user.setPassword(request.getPassword());

        User savedUser=userRepo.save(user);

        UserResponse response =new UserResponse();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());


        return response;

    }

    public List<UserResponse> getAllUser()
    {

        List<UserResponse> list=new ArrayList<>();
        List<User> user=userRepo.findAll();

        for(User u:user)
        {
            UserResponse response=new UserResponse();
            response.setId(u.getId());
            response.setFirstName(u.getFirstName());
            response.setLastName(u.getLastName());
            response.setEmail(u.getEmail());
            response.setRole(u.getRole());
            list.add(response);

        }
        return list;

    }

    public UserResponse getUserById(Long id)
    {
        User user=userRepo.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        UserResponse response =new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;

    }

    public UserResponse updateUser(Long id ,UserRequest request)
    {
        User user=userRepo.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User updatedUser=userRepo.save(user);
        UserResponse response =new UserResponse();
        response.setId(updatedUser.getId());
        response.setFirstName(updatedUser.getFirstName());
        response.setLastName(updatedUser.getLastName());
        response.setEmail(updatedUser.getEmail());
        response.setRole(updatedUser.getRole());
        return response;

    }
    public void deleteUserById(Long id)
    {
        User user=userRepo.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        userRepo.deleteById(id);

    }


}

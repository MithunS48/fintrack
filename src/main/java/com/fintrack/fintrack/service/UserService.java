package com.fintrack.fintrack.service;


import com.fintrack.fintrack.dto.user.ChangePassword;
import com.fintrack.fintrack.dto.user.UpdateUser;
import com.fintrack.fintrack.dto.user.UserRequest;
import com.fintrack.fintrack.dto.user.UserResponse;
import com.fintrack.fintrack.entity.User;
import com.fintrack.fintrack.enums.Role;
import com.fintrack.fintrack.exception.UserAlreadyExistsException;
import com.fintrack.fintrack.exception.UserNotFoundException;
import com.fintrack.fintrack.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Setter
@Getter
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;



    public UserResponse getUserProfile(UserDetails userDetails)
    {
        User user=userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()-> new UserNotFoundException("User not found"));
        UserResponse response =new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;

    }






    public UserResponse updateUser(UserDetails userDetails, UpdateUser updateUser)
    {
        User user=userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()-> new UserNotFoundException("User not found"));
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());


        User updatedUser=userRepo.save(user);
        UserResponse response =new UserResponse();
        response.setId(updatedUser.getId());
        response.setFirstName(updatedUser.getFirstName());
        response.setLastName(updatedUser.getLastName());
        response.setEmail(updatedUser.getEmail());
        response.setRole(updatedUser.getRole());
        return response;

    }

    public String changePassword(UserDetails userDetails, ChangePassword changePassword)
    {

        User user=userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()-> new UserNotFoundException("User not found"));

        if(!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword()))
        {
            throw  new RuntimeException("incorrect password");
        }

        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        userRepo.save(user);
        return  "Password changed successfully";

    }
    public String deleteProfile(UserDetails userDetails)
    {
        User user=userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()->new UserNotFoundException("user not found"));
        userRepo.delete(user);

        return "Account deleted successfully";

    }


}

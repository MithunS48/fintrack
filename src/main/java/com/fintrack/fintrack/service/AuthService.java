package com.fintrack.fintrack.service;

import com.fintrack.fintrack.dto.login.LoginRequest;
import com.fintrack.fintrack.dto.login.LoginResponse;
import com.fintrack.fintrack.dto.user.UserRequest;
import com.fintrack.fintrack.dto.user.UserResponse;
import com.fintrack.fintrack.entity.User;
import com.fintrack.fintrack.enums.Role;
import com.fintrack.fintrack.exception.UserAlreadyExistsException;
import com.fintrack.fintrack.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor

public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserResponse registration(UserRequest request)
    {

        if(userRepo.existsByEmail(request.getEmail()))
        {
            throw new UserAlreadyExistsException("user already exist");
        }
        User user =new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser=userRepo.save(user);

        UserResponse response =new UserResponse();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());


        return response;

    }

    public LoginResponse login(LoginRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        LoginResponse response=new LoginResponse();
        response.setMessage("successful");

        return response;
    }



}

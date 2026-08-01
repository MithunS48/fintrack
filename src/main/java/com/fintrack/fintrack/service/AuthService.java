package com.fintrack.fintrack.service;

import com.fintrack.fintrack.dto.login.LoginRequest;
import com.fintrack.fintrack.dto.login.LoginResponse;
import com.fintrack.fintrack.dto.login.RefreshTokenRequest;
import com.fintrack.fintrack.dto.user.UserRequest;
import com.fintrack.fintrack.dto.user.UserResponse;
import com.fintrack.fintrack.entity.RefreshToken;
import com.fintrack.fintrack.entity.User;
import com.fintrack.fintrack.enums.Role;
import com.fintrack.fintrack.exception.UserAlreadyExistsException;
import com.fintrack.fintrack.exception.UserNotFoundException;
import com.fintrack.fintrack.repository.RefreshTokenRepo;
import com.fintrack.fintrack.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@Service
@AllArgsConstructor

public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JWTService jwtService;
    private final RefreshTokenRepo refreshTokenRepo;

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

        UserDetails user =customUserDetailService.loadUserByUsername(request.getEmail());
        String token=jwtService.generateToken(user);
        String refresh= UUID.randomUUID().toString();

        User users = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setToken(refresh);
        refreshToken.setUser(users);
        refreshToken.setExpiryDate(LocalDateTime.now().plus(7, ChronoUnit.DAYS));
        refreshTokenRepo.save(refreshToken);

        LoginResponse response=new LoginResponse();
        response.setRefresh(refresh);
        response.setToken(token);


        return response;
    }


    public LoginResponse refreshToken(String token)
    {
        System.out.println("Received = " + token);
        RefreshToken refreshToken=refreshTokenRepo.findByToken(token).orElseThrow(()->new RuntimeException("token not found"));

        if(refreshToken.getExpiryDate().isBefore(LocalDateTime.now()))
        {
            throw new RuntimeException("Refresh token expired");
        }
        User user=refreshToken.getUser();
        UserDetails userDetails =customUserDetailService.loadUserByUsername(user.getEmail());
        String accessToken=jwtService.generateToken(userDetails);


        LoginResponse response=new LoginResponse();
        response.setRefresh(token);
        response.setToken(accessToken);

        return response;



    }



}

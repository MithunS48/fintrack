package com.fintrack.fintrack.service;

import com.fintrack.fintrack.entity.User;
import com.fintrack.fintrack.exception.UserNotFoundException;
import com.fintrack.fintrack.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Service
@AllArgsConstructor
@RestControllerAdvice
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepo.findByEmail(username).orElseThrow(()->new UserNotFoundException("user not found"));


        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).roles(user.getRole().name()).build();
    }
}

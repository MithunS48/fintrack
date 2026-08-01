package com.fintrack.fintrack.controller;

import com.fintrack.fintrack.dto.user.ChangePassword;
import com.fintrack.fintrack.dto.user.UpdateUser;
import com.fintrack.fintrack.dto.user.UserRequest;
import com.fintrack.fintrack.dto.user.UserResponse;
import com.fintrack.fintrack.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
   public UserResponse getProfile(@AuthenticationPrincipal UserDetails userDetails)
   {
       return userService.getUserProfile(userDetails);
   }

   @PutMapping("/profile")
   public UserResponse updateProfile(@AuthenticationPrincipal UserDetails userDetails, @Valid@RequestBody UpdateUser user)
   {
       return userService.updateUser(userDetails,user);
   }

   @PutMapping("/change-password")
   public String changePassword(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ChangePassword changePassword)
   {
       return userService.changePassword(userDetails,changePassword);
   }

   @DeleteMapping("/profile")
    public String deleteProfile(@AuthenticationPrincipal UserDetails userDetails)
   {
       return userService.deleteProfile(userDetails);
   }




}

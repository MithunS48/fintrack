package com.fintrack.fintrack.dto.user;

import com.fintrack.fintrack.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {


    private Long id;

    private String firstName;

    private String lastName;

    private String email;


    private Role role;
}

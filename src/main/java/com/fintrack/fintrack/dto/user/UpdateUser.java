package com.fintrack.fintrack.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUser {

    @NotBlank
    @Size(min =3)
    private String firstName;

    private String lastName;
}

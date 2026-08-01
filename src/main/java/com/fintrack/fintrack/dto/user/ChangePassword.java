package com.fintrack.fintrack.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePassword {

    @NotBlank
    private String oldPassword;

    @NotBlank
    @Size(min=6,max=12)
    private String newPassword;
}

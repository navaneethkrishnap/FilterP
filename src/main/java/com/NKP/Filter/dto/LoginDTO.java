package com.NKP.Filter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = "Enter username")
    private String username;
    @NotBlank(message = "Enter password")
    private String password;
}

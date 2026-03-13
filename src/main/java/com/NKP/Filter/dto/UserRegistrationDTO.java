package com.NKP.Filter.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDTO{


    @NotBlank(message = "Name is required")
    @Size(min = 4, message = "Name must be at least 4 characters")
    private String name;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$")
    private String username;

    @Size(min = 8,message = "Password must be at least 8 characters")
    private String password;

    @NotNull(message = "DOB is required")
    @Past(message = "DOB must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Email required")
    @Email(message = "Invalid email format")
    private String email;
}

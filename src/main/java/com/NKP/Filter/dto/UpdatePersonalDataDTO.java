package com.NKP.Filter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonalDataDTO {
    @Size(min = 4, message = "Name must be at least 4 characters")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9._]+$")
    private String username;
    @Size(min = 8,message = "Password must be at least 8 characters")
    private String password;
    @Email(message = "Invalid email format")
    private String email;
}

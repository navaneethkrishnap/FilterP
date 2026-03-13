package com.NKP.Filter.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostDTO {

    @Size(max=1000, message = "Description cannot exceed 1000 characters")
    private String description;

    private String city;

    private String area;

    private String criminalName;

    @Min(value = 5, message = "Criminal age must be at least 5")
    private Integer criminalAge;
}

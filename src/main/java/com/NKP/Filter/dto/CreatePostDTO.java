package com.NKP.Filter.dto;

import com.NKP.Filter.model.CrimeSceneType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class CreatePostDTO {


    @Size(max=1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotEmpty(message = "At least 1 image is required")
    private List<MultipartFile> images;

    @NotBlank(message = "City field must be filled")
    private String city;

    @Builder.Default
    private String area = "Unknown";

    private CrimeSceneType crimeSceneType;

    @Builder.Default
    private String criminalName = "Unknown";

    @Min(value = 5, message = "Criminal age must be at least 5")
    private Integer criminalAge;
}

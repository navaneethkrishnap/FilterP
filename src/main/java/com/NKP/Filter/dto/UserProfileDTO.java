package com.NKP.Filter.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserProfileDTO {
    private String name;
    private String username;
    private int postCount;
    private List<PostDTO> posts;

}

package com.NKP.Filter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDTO {

    private long postId;
    private String description;
    private String imageUrl;
}

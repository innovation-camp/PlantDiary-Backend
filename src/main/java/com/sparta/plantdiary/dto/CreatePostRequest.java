package com.sparta.plantdiary.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePostRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String thumbnail;
}

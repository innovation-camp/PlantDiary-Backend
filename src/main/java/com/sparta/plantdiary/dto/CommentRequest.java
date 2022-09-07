package com.sparta.plantdiary.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class CommentRequest {
    @NotEmpty
    private String content;

    @NotNull
    private Long postId;
}

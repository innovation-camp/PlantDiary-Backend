package com.sparta.plantdiary.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private WriterResponseDto writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

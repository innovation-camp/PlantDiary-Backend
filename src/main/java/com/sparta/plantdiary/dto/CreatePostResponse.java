package com.sparta.plantdiary.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreatePostResponse {
        private Long id;

        private String title;

        private String content;

        private String thumbnail;

        private Long countComment;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        private WriterResponseDto writer;
}

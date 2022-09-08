package com.sparta.plantdiary.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WriterResponseDto {
    private Long id;

    private String nickname;

}

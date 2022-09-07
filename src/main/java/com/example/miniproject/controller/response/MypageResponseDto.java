package com.example.miniproject.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MypageResponseDto {
    private Long id;
    private String nickname;
    private String email;
}

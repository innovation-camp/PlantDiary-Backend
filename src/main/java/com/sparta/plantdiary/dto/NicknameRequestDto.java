package com.sparta.plantdiary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NicknameRequestDto {

    @NotBlank
    @Pattern(regexp = "^[가-힣]*$")
    private String nickname;
}

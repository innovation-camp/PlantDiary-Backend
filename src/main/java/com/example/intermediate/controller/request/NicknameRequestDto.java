package com.example.intermediate.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NicknameRequestDto {
    
    @Pattern(regexp = "^[가-힣]*$")
    @NotBlank
    private String nickname;
}
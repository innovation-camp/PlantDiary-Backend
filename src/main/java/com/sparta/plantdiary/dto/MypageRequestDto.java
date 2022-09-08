package com.sparta.plantdiary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MypageRequestDto {

    @NotBlank
    @Pattern(regexp = "^[가-힣]*$")
    private String nickname;

    @NotBlank
    @Size(min = 6, max = 32)
    @Pattern(regexp = "[a-z\\d]*${3,32}")
    private String newPassword;

    @NotBlank
    private String passwordConfirm;
}
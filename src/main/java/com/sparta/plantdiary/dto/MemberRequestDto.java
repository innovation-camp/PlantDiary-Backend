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
public class MemberRequestDto {
    @NotBlank
    @Size(min = 4, max = 18)
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[가-힣]*$")
    private String nickname;

    @NotBlank
    @Size(min = 6, max = 32)
    @Pattern(regexp = "[a-z\\d]*${3,32}")
    private String password;

    @NotBlank
    private String passwordConfirm;
}
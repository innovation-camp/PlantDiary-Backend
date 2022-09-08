package com.example.intermediate.controller.request;

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


        @Pattern(regexp = "^[가-힣]*$")
        private String nickname;

        @Size(min = 4, max = 32)
        @Pattern(regexp = "[a-z\\d]*${3,32}")
        private String newPassword;

        private String passwordConfirm;
    }


package com.example.intermediate.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MypageRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class MemberRequestDto {

        @Size(min = 4, max = 18)
        @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?")
        private String email;


        @Size(min = 4, max = 12)
        @Pattern(regexp = "[a-zA-Z\\d]*${3,12}")
        private String nickname;


        @Size(min = 4, max = 32)
        @Pattern(regexp = "[a-z\\d]*${3,32}")
        private String password;


        private String passwordConfirm;
    }
}

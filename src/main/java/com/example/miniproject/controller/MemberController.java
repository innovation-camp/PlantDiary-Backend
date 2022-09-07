package com.example.miniproject.controller;

import com.example.miniproject.controller.request.*;
import com.example.miniproject.controller.response.ResponseDto;
import com.example.miniproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @RequestMapping(value = "/api/auth/register", method = RequestMethod.POST)
    public ResponseDto<?> register(@RequestBody @Valid MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    @RequestMapping(value = "/api/auth/login", method = RequestMethod.POST)
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
                                HttpServletResponse response
    ) {
        return memberService.login(requestDto, response);
    }

    //이메일 중복확인
    @RequestMapping(value = "/api/auth/email", method = RequestMethod.GET)
    public ResponseDto<?> emailCheak(@RequestBody @Valid EmailRequestDto requestDto) {
        return memberService.emailCheak(requestDto);
    }

    //닉네임 중복확인
    @RequestMapping(value = "/api/auth/nickname", method = RequestMethod.GET)
    public ResponseDto<?> nicknameCheak(@RequestBody @Valid NicknameRequestDto requestDto) {
        return memberService.nicknameCheak(requestDto);
    }

    //    //회원정보 조회
    @RequestMapping(value = "/api/auth/mypage", method = RequestMethod.GET)
    public ResponseDto<?> mypage(HttpServletRequest request) {
        return memberService.mypage(request);
    }

    //회원정보 수정
//    @RequestMapping(value = "/api/auth/mypage", method = RequestMethod.PUT)
//    public ResponseDto<?> mypageChange(@RequestBody @Valid MemberRequestDto requestDto, HttpServletRequest request) {
//        return memberService.mypageChange(requestDto, request);
//    }
}

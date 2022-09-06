package com.example.miniproject.service;

import com.example.miniproject.controller.request.*;
import com.example.miniproject.controller.response.MemberResponseDto;
import com.example.miniproject.controller.response.ResponseDto;
import com.example.miniproject.domain.Member;
import com.example.miniproject.jwt.TokenProvider;
import com.example.miniproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto requestDto){
        //email 중복확인 + 유효성 검사
        if(null != isPresentMemberByEmail(requestDto.getEmail())){
            return ResponseDto.fail("DUPLICATED_EMAIL","중복된 이메일 입니다.");
        }

        //닉네임 중복확인
        if(null != isPresentMemberByNickname(requestDto.getNickname())){
            return ResponseDto.fail("DUPLICATED_NICKNAME", "중복된 닉네임 입니다.");
        }

        //비밀번호 유효성확인 + 비밀번호확인이랑 같은지
        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .nickname(requestDto.getNickname())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();
        memberRepository.save(member);


        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .email(member.getEmail())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response){
        Member member = isPresentMemberByEmail(requestDto.getEmail());

        if(member == null){
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "사용자를 찾을 수 없습니다.");
        }
        if(!member.validatePassword(passwordEncoder, requestDto.getPassword())){
            return ResponseDto.fail("INVALID_MEMBER", "비밀번호가 일치하지 않습니다.");
        }


        TokenDto tokenDto = tokenProvider.generateTokenDto(member);

        Cookie cookie1 = new Cookie("AccessToken", tokenDto.getAccessToken());
//      cookie1.setMaxAge(tokenDto.getAccessTokenExpiresIn());
        cookie1.setPath("/");
        response.addCookie(cookie1);

        Cookie cookie2 = new Cookie("RefreshToken", tokenDto.getRefreshToken());
        cookie2.setPath("/");
        response.addCookie(cookie2);



        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .nickname(member.getNickname())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );
    }

//    public ResponseDto<?> mypage(HttpServletRequest request){
//        //헤더에서 token 가져오는 거 아니고 cookie에서 token 가져와야 함
////        if (null == request.getHeader("Refresh-Token")) {
////            return ResponseDto.fail("MEMBER_NOT_FOUND",
////                    "로그인이 필요합니다.");
////        }
////
////        if (null == request.getHeader("Authorization")) {
////            return ResponseDto.fail("MEMBER_NOT_FOUND",
////                    "로그인이 필요합니다.");
////        }
//        Member member = validateMember(request);
//        if (null == member) {
//            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//        }
//
//        //request에서 email 가져와서 그에 해당하는 member의 eamil, nickname, password을 줘야하는데? password는 그냥 주면 안 될텐데
//
//
//    }


    public ResponseDto<?> emailCheak(EmailRequestDto requestDto){
        if(null != isPresentMemberByEmail(requestDto.getEmail())){
            return ResponseDto.fail("DUPLICATED_EMAIL","중복된 이메일입니다.");
        }
        return ResponseDto.success("사용가능한 이메일입니다.");
    }

    public ResponseDto<?> nicknameCheak(NicknameRequestDto requestDto){
        if(null != isPresentMemberByEmail(requestDto.getNickname())){
            return ResponseDto.fail("DUPLICATED_EMAIL","중복된 닉네임입니다.");
        }
        return ResponseDto.success("사용가능한 닉네임입니다.");
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional
    public Member isPresentMemberByEmail(String email){
        Optional<Member> optionalUser = memberRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }

    @Transactional
    public Member isPresentMemberByNickname(String nickname){
        Optional<Member> optionalUser = memberRepository.findByNickname(nickname);
        return optionalUser.orElse(null);
    }

    //그리고 jwt를 쿠키에 담아서 보내주는 함수
//    public void TokenToCookie(TokenDto tokenDto, HttpServletResponse response){
//
//        CookieManager.setCookie(response, tokenDto);
//    }
}

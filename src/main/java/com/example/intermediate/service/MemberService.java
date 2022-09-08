package com.example.intermediate.service;

import com.example.intermediate.controller.request.*;
import com.example.intermediate.controller.response.*;
import com.example.intermediate.domain.Member;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.MemberRepository;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;

  @Transactional
  public ResponseDto<?> createMember(MemberRequestDto requestDto) {
    if (null != isPresentMemberByNickname(requestDto.getNickname())) {
      return ResponseDto.fail("DUPLICATED_NICKNAME",
          "중복된 닉네임 입니다.");
    }

    if (null != isPresentMemberByEmail(requestDto.getEmail())) {
      return ResponseDto.fail("DUPLICATED_EMAIL",
              "중복된 이메일 입니다.");
    }

    if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
      return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
          "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }

    Member member = Member.builder()
            .email(requestDto.getEmail())
            .nickname(requestDto.getNickname())
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
  public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
    Member member = isPresentMemberByEmail(requestDto.getEmail());
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "사용자를 찾을 수 없습니다.");
    }

    if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
      return ResponseDto.fail("INVALID_MEMBER", "비밀번호가 일치하지 않습니다.");
    }


    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
    tokenToHeaders(tokenDto, response);

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

  public ResponseDto<?> logout(HttpServletRequest request) {
    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }
    Member member = tokenProvider.getMemberFromAuthentication();
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "사용자를 찾을 수 없습니다.");
    }

    return tokenProvider.deleteRefreshToken(member);
  }

  public ResponseDto<?> mypage(HttpServletRequest request) {
    if (null == request.getHeader("Refresh-Token")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    if (null == request.getHeader("Authorization")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    Member member = validateMember(request);
    if (null == member) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }


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

  public ResponseDto<?> mypageUpdate(MypageRequestDto requestDto, HttpServletRequest request) {

    if (null == request.getHeader("Refresh-Token")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    if (null == request.getHeader("Authorization")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    Member member = validateMember(request);
    if (null == member) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }
    //비밀번호 확인해야함
    if(!member.validatePassword(passwordEncoder,requestDto.getPasswordConfirm())){
      return ResponseDto.fail("INVALID_PASSWORD", "비밀번호가 일치하지 않습니다.");
    }

    member.update(requestDto);

    return ResponseDto.success(member);
  }



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


  @Transactional(readOnly = true)
  public Member isPresentMemberByNickname(String nickname) {
    Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
    return optionalMember.orElse(null);
  }

  @Transactional(readOnly = true)
  public Member isPresentMemberByEmail(String email) {
    Optional<Member> optionalMember = memberRepository.findByEmail(email);
    return optionalMember.orElse(null);
  }

  public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
    response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
    response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
    response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
  }

}

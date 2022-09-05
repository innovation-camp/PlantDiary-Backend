package com.sparta.plantdiary.controller;

import com.sparta.plantdiary.command.CreatePostCommand;
import com.sparta.plantdiary.dto.CreatePostRequest;
import com.sparta.plantdiary.dto.CreatePostResponse;
import com.sparta.plantdiary.dto.WriterResponseDto;
import com.sparta.plantdiary.entity.Member;
import com.sparta.plantdiary.entity.Post;
import com.sparta.plantdiary.repository.MemberRepository;
import com.sparta.plantdiary.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    public final PostService postService;
    public final MemberRepository memberRepository; // TODO: 인증 기능 개발 후 삭제할 것

    @PostMapping("")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody @Valid CreatePostRequest request) {

        /**
         * TODO: 인증 기능 개발 후 삭제할 것
         * 아직 인증 기능이 개발되지 않아서 임시로 작성자 데이터를 만들었음.
         */
        Member writer = new Member("버블티", "bubble@bubble.com", "password");
        memberRepository.save(writer);

        CreatePostCommand command = new CreatePostCommand(request.getTitle(), request.getContent(), request.getThumbnail(), writer);

        Post post = postService.create(command);

        WriterResponseDto writerResponseDto = WriterResponseDto.builder()
                .id(writer.getId())
                .nickname(writer.getNickname())
                .build();

        CreatePostResponse response = CreatePostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .countComment(post.getCountComments())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .writer(writerResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

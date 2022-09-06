package com.sparta.plantdiary.controller;

import com.sparta.plantdiary.command.CreatePostCommand;
import com.sparta.plantdiary.command.UpdatePostCommand;
import com.sparta.plantdiary.dto.PostRequest;
import com.sparta.plantdiary.dto.PostResponse;
import com.sparta.plantdiary.dto.WriterResponseDto;
import com.sparta.plantdiary.entity.Member;
import com.sparta.plantdiary.entity.Post;
import com.sparta.plantdiary.error.NotFoundException;
import com.sparta.plantdiary.repository.MemberRepository;
import com.sparta.plantdiary.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    public final PostService postService;
    public final MemberRepository memberRepository; // TODO: 인증 기능 개발 후 삭제할 것

    @PostMapping("")
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostRequest request) {

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

        PostResponse response = PostResponse.builder()
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

    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getPosts() {
        List<Post> posts = postService.getAll();

        List<PostResponse> response = new ArrayList<>();

        for (Post post : posts) {
            WriterResponseDto writerResponseDto = WriterResponseDto.builder()
                    .id(post.getWriter().getId())
                    .nickname(post.getWriter().getNickname())
                    .build();

            PostResponse tmp = PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .thumbnail(post.getThumbnail())
                    .countComment(post.getCountComments())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .writer(writerResponseDto)
                    .build();

            response.add(tmp);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) throws NotFoundException {

        Post post = postService.getById(id);

        WriterResponseDto writerResponseDto = WriterResponseDto.builder()
                .id(post.getWriter().getId())
                .nickname(post.getWriter().getNickname())
                .build();

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .countComment(post.getCountComments())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .writer(writerResponseDto)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id) throws NotFoundException {
        postService.deleteById(id);
        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePostById(@PathVariable Long id, @RequestBody @Valid PostRequest request) throws NotFoundException {

        UpdatePostCommand command = new UpdatePostCommand(id, request.getTitle(), request.getContent(), request.getThumbnail());

        Post post = postService.updateById(command);

        WriterResponseDto writerResponseDto = WriterResponseDto.builder()
                .id(post.getWriter().getId())
                .nickname(post.getWriter().getNickname())
                .build();

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .countComment(post.getCountComments())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .writer(writerResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

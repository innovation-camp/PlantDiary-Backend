package com.sparta.plantdiary.controller;


import com.sparta.plantdiary.command.CreateCommentCommand;
import com.sparta.plantdiary.command.UpdateCommentCommand;
import com.sparta.plantdiary.dto.CommentRequest;
import com.sparta.plantdiary.dto.CommentResponse;
import com.sparta.plantdiary.dto.WriterResponseDto;
import com.sparta.plantdiary.entity.Comment;
import com.sparta.plantdiary.entity.Member;
import com.sparta.plantdiary.error.NotFoundException;
import com.sparta.plantdiary.repository.MemberRepository;
import com.sparta.plantdiary.service.CommentService;
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
@RequestMapping("/api/comments")
public class CommentController {

    public final CommentService commentService;
    public final MemberRepository memberRepository;


    @PostMapping("")
    public ResponseEntity<CommentResponse> createComment(@RequestBody @Valid CommentRequest request) throws NotFoundException {
        /**
         * TODO: 인증 기능 개발 후 삭제할 것
         * 아직 인증 기능이 개발되지 않아서 임시로 작성자 데이터를 만들었음.
         */
        Member writer = new Member("버블티", "bubble@bubble.com", "password");
        memberRepository.save(writer);

        CreateCommentCommand command = new CreateCommentCommand(request.getContent(), writer, request.getPostId());

        Comment comment = commentService.create(command);

        WriterResponseDto writerResponseDto = getWriterResponse(writer);
        CommentResponse response = getCommentResponse(comment, writerResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<CommentResponse>> getComments(@RequestParam Long postId) throws NotFoundException {
        List<Comment> comments = commentService.getAll(postId);

        List<CommentResponse> response = new ArrayList<>();

        for (Comment comment : comments) {
            WriterResponseDto writerResponseDto = getWriterResponse(comment.getWriter());
            CommentResponse tmp = getCommentResponse(comment, writerResponseDto);

            response.add(tmp);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateCommentById(@PathVariable Long id, @RequestBody @Valid CommentRequest request) throws NotFoundException {

        UpdateCommentCommand command = new UpdateCommentCommand(id, request.getContent(), request.getPostId());

        Comment comment = commentService.updateById(command);

        WriterResponseDto writerResponseDto = getWriterResponse(comment.getWriter());
        CommentResponse response = getCommentResponse(comment, writerResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    private WriterResponseDto getWriterResponse(Member writer) {
        return WriterResponseDto.builder()
                .id(writer.getId())
                .nickname(writer.getNickname())
                .build();
    }

    private CommentResponse getCommentResponse(Comment comment, WriterResponseDto writerResponseDto) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .writer(writerResponseDto)
                .build();
    }

}

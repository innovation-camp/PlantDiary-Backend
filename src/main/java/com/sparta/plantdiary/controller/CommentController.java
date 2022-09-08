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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Tag(name = "comments", description = "댓글 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    public final CommentService commentService;
    public final MemberRepository memberRepository;


    @Operation(summary = "create comment", description = "댓글 생성하기")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CommentResponse.class)))
    @ResponseBody
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

    @Operation(summary = "get comments", description = "댓글 리스트 가져오기")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CommentResponse.class)))
    @ResponseBody
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

    @Operation(summary = "update comment", description = "댓글 수정하기")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CommentResponse.class)))
    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateCommentById(@PathVariable Long id, @RequestBody @Valid CommentRequest request) throws NotFoundException {

        UpdateCommentCommand command = new UpdateCommentCommand(id, request.getContent(), request.getPostId());

        Comment comment = commentService.updateById(command);

        WriterResponseDto writerResponseDto = getWriterResponse(comment.getWriter());
        CommentResponse response = getCommentResponse(comment, writerResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "delete comment", description = "댓글 삭제하기")
    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletedCommentsById(@PathVariable Long id) throws NotFoundException {
        commentService.deleteById(id);
        return new ResponseEntity<>("댓글이 삭제되었습니다", HttpStatus.OK);
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

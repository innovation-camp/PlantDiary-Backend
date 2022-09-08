package com.sparta.plantdiary.controller;

import com.sparta.plantdiary.command.CreatePostCommand;
import com.sparta.plantdiary.command.UpdatePostCommand;
import com.sparta.plantdiary.dto.PostRequest;
import com.sparta.plantdiary.dto.PostResponse;
import com.sparta.plantdiary.dto.ThumbnailResponse;
import com.sparta.plantdiary.dto.WriterResponseDto;
import com.sparta.plantdiary.entity.Member;
import com.sparta.plantdiary.entity.Post;
import com.sparta.plantdiary.error.ForbiddenException;
import com.sparta.plantdiary.error.NotFoundException;
import com.sparta.plantdiary.jwt.TokenProvider;
import com.sparta.plantdiary.service.PostService;
import com.sparta.plantdiary.service.S3UploadService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Tag(name = "posts", description = "게시글 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    public final PostService postService;
    private final S3UploadService s3UploadService;
    public final TokenProvider tokenProvider;

    @Operation(summary = "upload images", description = "s3 이미지 업로드")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ThumbnailResponse.class)))
    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<ThumbnailResponse> uploadFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        String url = s3UploadService.upload(multipartFile);
        return new ResponseEntity<>(new ThumbnailResponse(url), HttpStatus.OK);
    }

    @Operation(summary = "create posts", description = "게시글 생성")
    @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = PostResponse.class)))
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostRequest request) {

        Member writer = tokenProvider.getMemberFromAuthentication();

        CreatePostCommand command = new CreatePostCommand(request.getTitle(), request.getContent(), request.getThumbnail(), writer);

        Post post = postService.create(command);

        WriterResponseDto writerResponseDto = getWriterResponse(writer);
        PostResponse response = getPostResponse(post, writerResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "get posts", description = "게시글들 가져오기")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PostResponse.class)))
    @ResponseBody
    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getPosts() {
        List<Post> posts = postService.getAll();

        List<PostResponse> response = new ArrayList<>();

        for (Post post : posts) {
            WriterResponseDto writerResponseDto = getWriterResponse(post.getWriter());
            PostResponse tmp = getPostResponse(post, writerResponseDto);

            response.add(tmp);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "get post", description = "게시글 상세 조회")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PostResponse.class)))
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) throws NotFoundException {

        Post post = postService.getById(id);

        WriterResponseDto writerResponseDto = getWriterResponse(post.getWriter());
        PostResponse response = getPostResponse(post, writerResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Operation(summary = "update posts", description = "게시글 삭제하기")
    @ApiResponse(responseCode = "200", description = "OK")
    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id) throws NotFoundException, ForbiddenException {
        postService.deleteById(id);

        return new ResponseEntity<>("게시글이 삭제되었습니다.", HttpStatus.OK);
    }

    @Operation(summary = "update posts", description = "게시글 수정하기")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PostResponse.class)))
    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePostById(@PathVariable Long id, @RequestBody @Valid PostRequest request) throws NotFoundException, ForbiddenException {

        UpdatePostCommand command = new UpdatePostCommand(id, request.getTitle(), request.getContent(), request.getThumbnail());

        Post post = postService.updateById(command);

        WriterResponseDto writerResponseDto = getWriterResponse(post.getWriter());
        PostResponse response = getPostResponse(post, writerResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    private WriterResponseDto getWriterResponse(Member writer) {
        return WriterResponseDto.builder()
                .id(writer.getId())
                .nickname(writer.getNickname())
                .build();
    }

    private PostResponse getPostResponse(Post post, WriterResponseDto writerResponseDto) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .countComment(post.getCountComments())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .writer(writerResponseDto)
                .build();
    }
}

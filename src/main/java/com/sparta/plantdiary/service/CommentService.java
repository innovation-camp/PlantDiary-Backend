package com.sparta.plantdiary.service;

import com.sparta.plantdiary.command.CreateCommentCommand;
import com.sparta.plantdiary.command.UpdateCommentCommand;
import com.sparta.plantdiary.entity.Comment;
import com.sparta.plantdiary.entity.Post;
import com.sparta.plantdiary.error.NotFoundException;
import com.sparta.plantdiary.repository.CommentRepository;
import com.sparta.plantdiary.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment create(CreateCommentCommand command) throws NotFoundException {
        Post post = postRepository.get(command.getPostId()).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        Comment comment = new Comment(command.getContent(), command.getWriter(), post);
        return commentRepository.save(comment);
    }

    public List<Comment> getAll(Long postId) throws NotFoundException {
        postRepository.get(postId).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        List<Comment> comments = commentRepository.getAll(postId);
        return comments;
    }

    public Comment updateById(UpdateCommentCommand command) throws NotFoundException {
        postRepository.get(command.getPostId()).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(command.getId()).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        comment.setContent(command.getContent());
        commentRepository.save(comment);

        return comment;
    }

}

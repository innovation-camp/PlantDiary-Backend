package com.sparta.plantdiary.service;

import com.sparta.plantdiary.command.CreateCommentCommand;
import com.sparta.plantdiary.entity.Comment;
import com.sparta.plantdiary.entity.Member;
import com.sparta.plantdiary.entity.Post;
import com.sparta.plantdiary.error.NotFoundException;
import com.sparta.plantdiary.repository.CommentRepository;
import com.sparta.plantdiary.repository.MemberRepository;
import com.sparta.plantdiary.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    Member writer;
    Post post;
    Comment comment;
    Long weirdId = 2147483647L;

    @Autowired
    CommentService commentService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    public void setUp() throws Exception {
        writer = new Member("nickname", "email", "password");
        memberRepository.save(writer);

        post = new Post("title", "content", "thumbnail", writer);
        postRepository.save(post);

        comment = new Comment("content", writer, post);
        commentRepository.save(comment);
    }

    @Test
    public void testSetup() {
        assertNotNull(writer);
        assertNotNull(post);
        assertNotNull(comment);
    }

    @Test
    public void testCreate() throws NotFoundException {
        CreateCommentCommand command = new CreateCommentCommand("댓글 내용", writer, post.getId());
        Comment newComment = commentService.create(command);

        assertNotNull(newComment);
        assertNotNull(newComment.getId());
        assertEquals(command.getContent(), newComment.getContent());
        assertEquals(command.getWriter(), newComment.getWriter());
        assertEquals(command.getPostId(), newComment.getPost().getId());
    }

    @Test
    public void testGetAll() throws NotFoundException {
        List<Comment> comments = commentService.getAll(post.getId());

        assertNotNull(comments);
    }


}
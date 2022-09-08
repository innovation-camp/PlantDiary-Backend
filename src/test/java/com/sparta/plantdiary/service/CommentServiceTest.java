package com.sparta.plantdiary.service;

import com.sparta.plantdiary.command.CreateCommentCommand;
import com.sparta.plantdiary.command.UpdateCommentCommand;
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
import java.util.Optional;

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

    @Test
    public void testUpdateById() throws NotFoundException {
        UpdateCommentCommand command = new UpdateCommentCommand(comment.getId(), "업데이트된 댓글", post.getId());
        Comment updatedComment = commentService.updateById(command);

        assertEquals(comment.getId(), updatedComment.getId());
        assertEquals(command.getContent(), updatedComment.getContent());
        assertEquals(post.getWriter(), updatedComment.getWriter());
    }

    @Test
    public void testUpdateByIdWithWrongIdFail() {
        UpdateCommentCommand command = new UpdateCommentCommand(weirdId, "잘못된 댓글 id", post.getId());

        assertThrows(NotFoundException.class, () -> commentService.updateById(command));
    }

    @Test
    public void testUpdateByIdWithWrongPostIdFail() {
        UpdateCommentCommand command = new UpdateCommentCommand(comment.getId(), "잘못된 댓글 id", weirdId);

        assertThrows(NotFoundException.class, () -> commentService.updateById(command));
    }

    @Test
    public void testDeleteById() throws NotFoundException {
        Long id = comment.getId();
        commentService.deleteById(id);
        Optional<Comment> deletedComment = commentRepository.findById(id);
        assertFalse(deletedComment.isPresent());
    }

    @Test
    public void testDeleteByIdFail() {
        assertThrows(NotFoundException.class, () -> commentService.deleteById(weirdId));
    }
}
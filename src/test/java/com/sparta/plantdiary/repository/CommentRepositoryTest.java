package com.sparta.plantdiary.repository;

import com.sparta.plantdiary.entity.Comment;
import com.sparta.plantdiary.entity.Member;
import com.sparta.plantdiary.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Transactional
class CommentRepositoryTest {

    public Member writer;
    public Post post;

    public Comment comment;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    public void setUp() throws Exception {
        writer = new Member("nickname", "email@email.com", "password");
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
    public void testCreate() {
        Comment newComment = new Comment("content", writer, post);
        commentRepository.save(newComment);

        assertNotNull(newComment);
        assertNotNull(newComment.getId());
        assertEquals("content", newComment.getContent());
        assertEquals(writer, newComment.getWriter());
        assertEquals(post, newComment.getPost());
    }

    @Test
    public void testGetAll() {
        List<Comment> comments = commentRepository.getAll(post.getId());
        assertNotNull(comments);
    }


}
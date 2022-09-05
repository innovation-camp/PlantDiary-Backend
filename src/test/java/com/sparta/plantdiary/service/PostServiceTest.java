package com.sparta.plantdiary.service;

import com.sparta.plantdiary.command.CreatePostCommand;
import com.sparta.plantdiary.entity.Member;
import com.sparta.plantdiary.entity.Post;
import com.sparta.plantdiary.error.NotFoundException;
import com.sparta.plantdiary.repository.MemberRepository;
import com.sparta.plantdiary.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    Member writer;
    Post post;


    @Autowired
    PostService postService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;


    @BeforeEach
    public void setUp() throws Exception {
        writer = new Member("nickname", "email", "password");
        memberRepository.save(writer);

        post = new Post("title", "content", "thumbnail", writer);
        postRepository.save(post);
    }

    @Test
    public void testSetup() {
        assertNotNull(writer);
        assertNotNull(post);
    }

    @Test
    void testCreated() {

        CreatePostCommand command = new CreatePostCommand("title", "content", "thumbnail", writer);
        Post newPost = postService.create(command);

        assertNotNull(newPost);
        assertNotNull(newPost.getId());
        assertEquals(command.getTitle(), newPost.getTitle());
        assertEquals(command.getContent(), newPost.getContent());
        assertEquals(command.getThumbnail(), newPost.getThumbnail());
        assertEquals(command.getWriter(), newPost.getWriter());
    }

    @Test
    void testGetByIdSuccess() {
        Long id = post.getId();

        Post foundPost = assertDoesNotThrow(() -> postService.getById(id));

        assertNotNull(foundPost);
        assertEquals(post.getId(), foundPost.getId());
        assertEquals(post.getTitle(), foundPost.getTitle());
        assertEquals(post.getContent(), foundPost.getContent());
        assertEquals(post.getThumbnail(), foundPost.getThumbnail());
        assertEquals(post.getWriter(), foundPost.getWriter());
    }

    @Test
    void testGetByIdFail() {
        Long id = 2147483647L;

        assertThrows(NotFoundException.class, () -> postService.getById(id));
    }
}
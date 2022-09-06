package com.sparta.plantdiary.repository;

import com.sparta.plantdiary.entity.Member;
import com.sparta.plantdiary.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class PostRepositoryTest {

    public Member writer;
    public Post post;


    @Autowired
    PostRepository postRepository;

    @Autowired

    MemberRepository memberRepository;

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
    }

    @Test
    public void testCreate() {

        Post newPost = new Post("title", "content", "thumbnail", writer);
        postRepository.save(newPost);

        assertNotNull(newPost);
        assertNotNull(newPost.getId());
        assertEquals("title", newPost.getTitle());
        assertEquals("content", newPost.getContent());
        assertEquals("thumbnail", newPost.getThumbnail());
        assertEquals(writer, newPost.getWriter());
    }

    @Test
    public void testGet() {
        Long id = post.getId();

        Post foundPost = postRepository.get(id).get();

        assertNotNull(foundPost);
        assertNotNull(foundPost.getId());

        assertEquals(post.getId(), foundPost.getId());
        assertEquals(post.getContent(), foundPost.getContent());
        assertEquals(post.getThumbnail(), foundPost.getThumbnail());
        assertEquals(post.getWriter(), foundPost.getWriter());

    }

    @Test
    public void testDelete() {
        Long id = post.getId();

        postRepository.deleteById(id);

        Optional<Post> deletedPost = postRepository.findById(id);

        assertFalse(deletedPost.isPresent());
    }

    @Test
    public void testGetAll() {
        List<Post> posts = postRepository.getAll();

        assertNotNull(posts);
    }
}
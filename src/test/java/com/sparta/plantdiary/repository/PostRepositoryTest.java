package com.sparta.plantdiary.repository;

import com.sparta.plantdiary.entity.Member;
import com.sparta.plantdiary.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class PostRepositoryTest {

    public Member writer;
    public HashMap<String, String> expectedMember = new HashMap<>();

    @Autowired
    PostRepository postRepository;

    @Autowired

    MemberRepository memberRepository;

    @BeforeEach
    public void setUp() throws Exception {
        expectedMember.put("nickname", "nickname");
        expectedMember.put("email", "email@email.com");
        expectedMember.put("password", "password");

        writer = new Member(expectedMember.get("nickname"), expectedMember.get("email"), expectedMember.get("password"));
        memberRepository.save(writer);
    }

    @Test
    public void testSetup() {
        assertNotNull(writer);
    }

    @Test
    public void testCreate() {
        HashMap<String, String> expected = new HashMap<>();
        expected.put("title", "title");
        expected.put("content", "content");
        expected.put("thumbnail", "thumbnail");

        Post post = new Post(expected.get("title"), expected.get("content"), expected.get("thumbnail"), writer);
        postRepository.save(post);

        assertNotNull(post);
        assertNotNull(post.getId());
        assertEquals(expected.get("title"), post.getTitle());
        assertEquals(expected.get("content"), post.getContent());
        assertEquals(expected.get("thumbnail"), post.getThumbnail());
        assertEquals(writer, post.getWriter());
    }

}
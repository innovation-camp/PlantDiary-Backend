package com.sparta.plantdiary.service;

import com.sparta.plantdiary.command.CreatePostCommand;
import com.sparta.plantdiary.entity.Member;
import com.sparta.plantdiary.entity.Post;
import com.sparta.plantdiary.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class PostServiceTest {

    public Member writer;
    public HashMap<String, String> expectedMember = new HashMap<>();


    @Autowired
    PostService postService;

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
    void testCreated() {

        CreatePostCommand command = new CreatePostCommand("title", "content", "thumbnail", writer);
        Post post = postService.create(command);

        assertNotNull(post);
        assertNotNull(post.getId());
        assertEquals(command.getTitle(), post.getTitle());
        assertEquals(command.getContent(), post.getContent());
        assertEquals(command.getThumbnail(), post.getThumbnail());
        assertEquals(command.getWriter(), post.getWriter());
    }
}
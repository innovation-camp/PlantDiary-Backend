package com.sparta.plantdiary.repository;

import com.sparta.plantdiary.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void create() {
        HashMap<String, String> expected = new HashMap<>();
        expected.put("nickname", "nickname");
        expected.put("email", "email@email.com");
        expected.put("password", "password");

        Member member = new Member(expected.get("nickname"), expected.get("email"), expected.get("password"));
        Member actual = memberRepository.save(member);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertEquals(expected.get("nickname"), actual.getNickname());
    }
}
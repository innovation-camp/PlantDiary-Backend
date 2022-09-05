package com.sparta.plantdiary.repository;

import com.sparta.plantdiary.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void create() {
        Member member = new Member("nickname", "email@email.com", "password");
        Member saved = memberRepository.save(member);
        assertNotNull(member);
        assertEquals(member, saved);
        assertNotNull(member.getId());
    }
}
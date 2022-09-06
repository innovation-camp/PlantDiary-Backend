package com.example.miniproject.repository;

import com.example.miniproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);

}
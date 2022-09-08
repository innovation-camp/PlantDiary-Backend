package com.sparta.plantdiary.repository;

import com.sparta.plantdiary.entity.Member;
import com.sparta.plantdiary.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}

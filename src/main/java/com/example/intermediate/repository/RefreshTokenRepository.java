package com.example.intermediate.repository;

import com.example.intermediate.repository.domain.Member;
import com.example.intermediate.repository.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByMember(Member member);
}

package com.sparta.plantdiary.repository;

import com.sparta.plantdiary.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

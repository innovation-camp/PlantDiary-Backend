package com.sparta.plantdiary.repository;

import com.sparta.plantdiary.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

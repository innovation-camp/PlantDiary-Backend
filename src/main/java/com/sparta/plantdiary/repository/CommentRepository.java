package com.sparta.plantdiary.repository;

import com.sparta.plantdiary.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select comment from Comment comment join fetch comment.writer where comment.post.id=:id")
    List<Comment> getAll(Long id);
}

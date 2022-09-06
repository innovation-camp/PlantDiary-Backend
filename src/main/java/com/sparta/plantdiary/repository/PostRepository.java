package com.sparta.plantdiary.repository;

import com.sparta.plantdiary.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select post from Post post join fetch post.writer where post.id=:id")
    Optional<Post> get(Long id);

    @Query("select post from Post post join fetch post.writer")
    List<Post> getAll();
}

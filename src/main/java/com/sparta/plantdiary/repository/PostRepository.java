package com.sparta.plantdiary.repository;

import com.sparta.plantdiary.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

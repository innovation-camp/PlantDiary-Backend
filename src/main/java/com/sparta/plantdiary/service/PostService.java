package com.sparta.plantdiary.service;

import com.sparta.plantdiary.command.CreatePostCommand;
import com.sparta.plantdiary.entity.Post;
import com.sparta.plantdiary.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create(CreatePostCommand command) {
        Post post = new Post(command.getTitle(), command.getContent(), command.getThumbnail(), command.getWriter());
        return postRepository.save(post);
    }

}

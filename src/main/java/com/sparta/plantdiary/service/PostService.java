package com.sparta.plantdiary.service;

import com.sparta.plantdiary.command.CreatePostCommand;
import com.sparta.plantdiary.command.UpdatePostCommand;
import com.sparta.plantdiary.entity.Post;
import com.sparta.plantdiary.error.NotFoundException;
import com.sparta.plantdiary.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create(CreatePostCommand command) {
        Post post = new Post(command.getTitle(), command.getContent(), command.getThumbnail(), command.getWriter());
        return postRepository.save(post);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public Post getById(Long id) throws NotFoundException {
        Post post = postRepository.get(id).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        return post;
    }

    public void deleteById(Long id) throws NotFoundException {
        postRepository.get(id).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        postRepository.deleteById(id);
    }

    public Post updateById(UpdatePostCommand command) throws NotFoundException {
        Post post = postRepository.get(command.getId()).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        post.setTitle(command.getTitle());
        post.setContent(command.getContent());
        post.setThumbnail(command.getThumbnail());
        postRepository.save(post);

        return post;
    }

}

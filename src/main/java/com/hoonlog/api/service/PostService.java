package com.hoonlog.api.service;

import com.hoonlog.api.domain.Post;
import com.hoonlog.api.repository.PostRepository;
import com.hoonlog.api.request.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void save(PostDto postDto) {

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        postRepository.save(post);
    }
}

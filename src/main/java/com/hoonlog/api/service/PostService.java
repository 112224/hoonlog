package com.hoonlog.api.service;

import com.hoonlog.api.domain.Post;
import com.hoonlog.api.repository.PostRepository;
import com.hoonlog.api.request.PostRequest;
import com.hoonlog.api.service.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void save(PostRequest postRequest) {

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();

        postRepository.save(post);
    }

    public PostDto get(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostDto> getList() {
        return postRepository.findAll().stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build())
                .collect(toList());
    }
}

package com.hoonlog.api.controller;

import com.hoonlog.api.domain.Post;
import com.hoonlog.api.repository.PostRepository;
import com.hoonlog.api.request.PostDto;
import com.hoonlog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostDto params) {
        log.info("params = {}" , params.toString());
        postService.save(params);
    }

    /**
     * /posts -> 글 전체 조회
     * /posts/{postId} -> 단건 조회
     */

    @GetMapping("/posts/{postId}")
    public Post get(@PathVariable Long postId) {
        return postService.get(postId);
    }
}

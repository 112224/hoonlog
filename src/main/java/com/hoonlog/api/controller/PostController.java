package com.hoonlog.api.controller;

import com.hoonlog.api.domain.Post;
import com.hoonlog.api.request.PostRequest;
import com.hoonlog.api.response.PostResponse;
import com.hoonlog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostRequest params) {
        log.info("params = {}" , params.toString());
        postService.save(params);
    }

    /**
     * /posts -> 글 전체 조회
     * /posts/{postId} -> 단건 조회
     */

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        Post post = postService.get(postId);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}

package com.hoonlog.api.controller;

import com.hoonlog.api.domain.Post;
import com.hoonlog.api.request.PostRequest;
import com.hoonlog.api.response.PostResponse;
import com.hoonlog.api.service.PostService;
import com.hoonlog.api.service.dto.PostDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        PostDto postDto = postService.get(postId);

        return PostResponse.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(Pageable pageable) {
        List<PostDto> results = postService.getList(pageable);
        return results.stream()
                .map(PostResponse::new)
                .collect(toList());
    }
}

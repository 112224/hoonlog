package com.hoonlog.api.controller;

import com.hoonlog.api.request.PostEdit;
import com.hoonlog.api.request.PostRequest;
import com.hoonlog.api.request.PostSearch;
import com.hoonlog.api.response.PostResponse;
import com.hoonlog.api.service.PostService;
import com.hoonlog.api.service.dto.PostDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostRequest params) {
        log.info("params = {}", params.toString());
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
    public List<PostResponse> getList(PostSearch postSearch) {
        List<PostDto> results = postService.getList(postSearch);
        return results.stream()
                .map(PostResponse::new)
                .collect(toList());
    }

    /**
     *
     */
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit) {
        postService.edit(postId, postEdit);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}

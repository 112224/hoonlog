package com.hoonlog.api.response;

import com.hoonlog.api.service.dto.PostDto;
import lombok.Builder;
import lombok.Getter;

import static java.lang.Math.min;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;

    public PostResponse(PostDto postDto) {
        this.id = postDto.getId();
        this.title = postDto.getTitle().substring(0, min(10, postDto.getTitle().length()));
        this.content = postDto.getContent();
    }
    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, min(10, title.length()));
        this.content = content;
    }
}

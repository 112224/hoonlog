package com.hoonlog.api.service.dto;

import com.hoonlog.api.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDto {

    private final Long id;
    private final String title;
    private final String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
    @Builder
    public PostDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}

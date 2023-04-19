package com.hoonlog.api.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDto {

    private final Long id;
    private final String title;
    private final String content;

    @Builder
    public PostDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}

package com.hoonlog.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class PostRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    public String title;

    @NotBlank(message = "내용을 입력해주세요.")
    public String content;

    @Builder
    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

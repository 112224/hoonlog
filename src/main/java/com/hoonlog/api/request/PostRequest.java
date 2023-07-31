package com.hoonlog.api.request;

import com.hoonlog.api.exception.InvalidRequestException;
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

    public void validate() {
        if (title.contains("바보")) {
            throw new InvalidRequestException("title", "제목에 바보를 포함할 수 없습니다.");
        }
    }
}

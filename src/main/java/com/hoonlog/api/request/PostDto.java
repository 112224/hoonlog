package com.hoonlog.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PostDto {

    @NotBlank
    public String title;

    @NotBlank
    public String content;
}

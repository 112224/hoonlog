package com.hoonlog.api.controller;

import com.hoonlog.api.request.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public String post(@RequestBody PostDto params) {
        log.info("params = {}" , params.toString());
        return "Hello World";
    }
}

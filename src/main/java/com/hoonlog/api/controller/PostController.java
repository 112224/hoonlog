package com.hoonlog.api.controller;

import com.hoonlog.api.request.PostDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostDto params, BindingResult result) {
        log.info("params = {}" , params.toString());
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField();
            String defaultMessage = firstFieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, defaultMessage);
            return error;
        }

        return Map.of();
    }
}

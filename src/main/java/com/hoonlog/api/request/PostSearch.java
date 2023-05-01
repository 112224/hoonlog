package com.hoonlog.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
public class PostSearch {

    public static final int MAX_SIZE = 30;

    private int page;
    private int size;

    @Builder
    public PostSearch(Integer page, Integer size) {
        this.page = validatePageValue(page);
        this.size = size == null ? 10 : size;
    }

    private int validatePageValue(Integer page) {
        if (page == null) {
            return 1;
        }
        return max(page, 1);
    }

    public long getOffset() {
        return (long) (page - 1) * min(size, MAX_SIZE);
    }
}

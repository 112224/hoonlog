package com.hoonlog.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearch {

    private int page;
    private int size;

    @Builder
    public PostSearch(Integer page, Integer size) {
        this.page = validatePageValue(page);
        this.size = size == null ? 10 : size;
    }

    private int validatePageValue(Integer page) {
        if (page == null || page < 1 ) {
            return 1;
        }
        return page;
    }

    public long getOffset() {
        return (long) (getPage() - 1) * getSize();
    }
}

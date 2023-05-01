package com.hoonlog.api.repository;

import com.hoonlog.api.domain.Post;
import com.hoonlog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}

package com.hoonlog.api.repository;

import com.hoonlog.api.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(int page);
}

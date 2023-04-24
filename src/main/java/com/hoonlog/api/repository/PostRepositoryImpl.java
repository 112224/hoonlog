package com.hoonlog.api.repository;

import com.hoonlog.api.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hoonlog.api.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(int page) {
        return jpaQueryFactory
                .selectFrom(post)
                .limit(10)
                .offset((page - 1) * 10L)
                .orderBy(post.id.desc())
                .fetch();
    }
}

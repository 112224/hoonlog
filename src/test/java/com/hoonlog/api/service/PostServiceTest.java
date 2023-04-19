package com.hoonlog.api.service;

import com.hoonlog.api.domain.Post;
import com.hoonlog.api.repository.PostRepository;
import com.hoonlog.api.request.PostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("Save post")
    public void test1() {
        //given
        PostRequest postRequest = PostRequest.builder()
                .title("title!")
                .content("this is content!!")
                .build();

        //when
        postService.save(postRequest);

        //then
        assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("title!");
        assertThat(post.getContent()).isEqualTo("this is content!!");

    }
    @Test
    @DisplayName("단건 조회 테스트 -> 정상")
    public void singleTest() throws Exception {
        //given
        Post reqPost = Post.builder()
                .title("title")
                .content("content")
                .build();

        //when
        postRepository.save(reqPost);

        //then
        Post post = postService.get(reqPost.getId());
        assertThat(post).isNotNull();
        assertThat(post.getContent()).isEqualTo("content");
        assertThat(post.getTitle()).isEqualTo("title");
    }

    @Test
    @DisplayName("단건 조회 테스트 -> 에러")
    public void singleTest2() throws Exception {
        //given
        Long postId = 1L;
        //when
//        Post post = postService.get(postId);

        //then
        assertThrows(IllegalArgumentException.class,
                () -> postService.get(postId)
        );
    }
}
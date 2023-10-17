package com.hoonlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoonlog.api.domain.Post;
import com.hoonlog.api.repository.PostRepository;
import com.hoonlog.api.request.PostEdit;
import com.hoonlog.api.request.PostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성 /posts 요청시 Title 값은 필수")
    public void valid() throws Exception {
        //given
        PostRequest postRequest = PostRequest.builder()
                .content("글내용")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(postRequest);
        //expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목을 입력해주세요."))
                .andDo(print());
    }
    @Test
    @DisplayName("글 작성 /posts 요청 시 DB에 값이 저장")
    public void saveTest() throws Exception {
        //given
        PostRequest postRequest = PostRequest.builder()
                .title("제목!")
                .content("글내용")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(postRequest);

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertThat(postRepository.count()).isEqualTo(1L);

        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("제목!");
        assertThat(post.getContent()).isEqualTo("글내용");
    }

    @Test
    public void 부적절한_제목() throws Exception {
        //given
        PostRequest postRequest = PostRequest.builder()
                .title("제목은 바보!")
                .content("글내용")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(postRequest);

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("단건 조회")
    public void test4() throws Exception {
        //given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("단건 조회")
    public void test5() throws Exception {
        //given
        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("다건 조회")
    public void test6() throws Exception {
        //given
        Post post1 = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        postRepository.save(post1);

        Post post2 = Post.builder()
                .title("1234567890")
                .content("bar1234")
                .build();
        postRepository.save(post2);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(post2.getId()))
                .andExpect(jsonPath("$[0].title").value("1234567890"))
                .andExpect(jsonPath("$[0].content").value("bar1234"))
                .andExpect(jsonPath("$[1].id").value(post1.getId()))
                .andExpect(jsonPath("$[1].title").value("1234567890"))
                .andExpect(jsonPath("$[1].content").value("bar"))
                .andDo(print());
    }

    @Test
    public void test7() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(0, 30)
                .mapToObj(i -> Post.builder()
                        .title("Title " + i)
                        .content("Content " + i)
                        .build()
                ).toList();

        postRepository.saveAll(requestPosts);
        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=2&size=5&sort=id,desc")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].title").value("Title 24"))
                .andExpect(jsonPath("$[0].content").value("Content 24"))
                .andDo(print());
    }

    @Test
    public void updateTest() throws Exception{
        //given
        Post post = Post.builder()
                .title("hoon!!")
                .content("Spring So Convenience!!")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("Hoon!")
                .content("Update the Post!")
                .build();

        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제")
    public void deletePost() throws Exception {
        //given
        Post post = Post.builder()
                .title("hoon!!")
                .content("Spring So Convenience!!")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()
                ).andDo(print());


    }

    @Test
    public void 존재하지_않는_게시글_조회() throws Exception {
        //expected
        mockMvc.perform(delete("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void 존재하지_않는_게시글_수정() throws Exception {
        // given
        PostEdit postEdit = PostEdit.builder()
                .title("Hoon!")
                .content("Update the Post!")
                .build();
        //expected
        mockMvc.perform(patch("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
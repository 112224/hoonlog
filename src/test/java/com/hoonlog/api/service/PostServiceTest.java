package com.hoonlog.api.service;

import com.hoonlog.api.domain.Post;
import com.hoonlog.api.repository.PostRepository;
import com.hoonlog.api.request.PostEdit;
import com.hoonlog.api.request.PostRequest;
import com.hoonlog.api.request.PostSearch;
import com.hoonlog.api.service.dto.PostDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.validation.Validator;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        PostDto post = postService.get(reqPost.getId());
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

        //expected
        assertThrows(IllegalArgumentException.class,
                () -> postService.get(postId)
        );
    }

//    @Test
//    @DisplayName("다건조회 테스트 - 페이징")
//    public void getPostListTest() throws Exception {
//        //given
//        List<Post> requestPosts = IntStream.range(0, 30)
//                .mapToObj(i -> Post.builder()
//                            .title("Title " + i)
//                            .content("Content " + i)
//                            .build()
//                ).toList();
//
//        postRepository.saveAll(requestPosts);
//
//        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
//        //when
//        List<PostDto> list = postService.getList(postSearch);
//
//        //then
//        assertThat(list).isNotNull();
//        assertThat(list.size()).isEqualTo(5);
//        assertThat(list.get(0).getTitle()).isEqualTo("Title 0");
//        assertThat(list.get(4).getTitle()).isEqualTo("Title 4");
//
//    }

    @Test
    @DisplayName("다건조회 테스트 - querydsl")
    public void getQdlPostListTest() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(0, 30)
                .mapToObj(i -> Post.builder()
                        .title("Title " + i)
                        .content("Content " + i)
                        .build()
                ).toList();

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();
        //when
        List<PostDto> list = postService.getList(postSearch);

        //then
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(10);
        assertThat(list.get(0).getTitle()).isEqualTo("Title 29");
        assertThat(list.get(4).getTitle()).isEqualTo("Title 25");

    }

    @Test
    @DisplayName("게시글 제목 수정")
    public void updatePostTitle() throws Exception {
        //given
        PostRequest postRequest = PostRequest.builder()
                .title("hoon!!")
                .content("Spring So Convenience!!")
                .build();

        //when
        Long savedId = postService.save(postRequest);

        PostEdit postEdit = PostEdit.builder()
                .title("Hoon!")
                .content("Update the Post!")
                .build();
        postService.edit(savedId, postEdit);

        Post post = postRepository.findById(savedId)
                .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다. postId =" + savedId));

        //then
        assertThat(post.getTitle()).isEqualTo("Hoon!");
        assertThat(post.getContent()).isEqualTo("Update the Post!");
    }

    @Test
    @DisplayName("게시글 제목 수정_빌더 이용")
    public void updatePostTitle2() throws Exception {
        //given
        PostRequest postRequest = PostRequest.builder()
                .title("hoon!!")
                .content("Spring So Convenience!!")
                .build();

        //when
        Long savedId = postService.save(postRequest);

        PostEdit postEdit = PostEdit.builder()
                .title("Hoon!")
                .content("Update the Post!")
                .build();
        postService.editUsingBuilder(savedId, postEdit);

        Post post = postRepository.findById(savedId)
                .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다. postId =" + savedId));

        //then
        assertThat(post.getTitle()).isEqualTo("Hoon!");
        assertThat(post.getContent()).isEqualTo("Update the Post!");
    }

    @Test
    @DisplayName("게시글 삭제")
    public void deletePost() throws Exception {
        //given
        PostRequest postRequest = PostRequest.builder()
                .title("hoon!!")
                .content("Spring So Convenience!!")
                .build();

        Long savedId = postService.save(postRequest);

        //when
        postService.delete(savedId);
        //then
        assertThat(postRepository.count()).isEqualTo(0);
    }
}
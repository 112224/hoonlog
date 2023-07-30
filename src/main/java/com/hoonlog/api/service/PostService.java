package com.hoonlog.api.service;

import com.hoonlog.api.domain.Post;
import com.hoonlog.api.domain.PostEditor;
import com.hoonlog.api.exception.PostNotFoundException;
import com.hoonlog.api.repository.PostRepository;
import com.hoonlog.api.request.PostEdit;
import com.hoonlog.api.request.PostRequest;
import com.hoonlog.api.request.PostSearch;
import com.hoonlog.api.service.dto.PostDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long save(@Valid PostRequest postRequest) {

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();

        postRepository.save(post);
        return post.getId();
    }

    public PostDto get(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostDto> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostDto::new)
                .collect(toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        post.changePost(postEdit);
    }

    @Transactional
    public void editUsingBuilder(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        // 전체 내용이 넘어오는 경우
        PostEditor postEditor = editorBuilder
                .title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        // 수정이 필요한 내용만 넘어오는 경우
        /*
        if (postEdit.getTitle() != null) {
            editorBuilder.title(postEdit.getTitle());
        }
        if (postEdit.getContent() != null) {
            editorBuilder.content(postEdit.getContent());
        }
         */

        post.edit(postEditor);
    }

    public void delete(Long savedId) {
        Post post = postRepository.findById(savedId).orElseThrow(PostNotFoundException::new);

        postRepository.delete(post);
    }

}

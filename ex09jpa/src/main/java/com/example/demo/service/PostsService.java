package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Posts.Posts;
import com.example.demo.domain.Posts.PostsListResponseDto;
import com.example.demo.domain.Posts.PostsRepository;
import com.example.demo.domain.Posts.PostsResponseDto;
import com.example.demo.domain.Posts.PostsUpdateRequestDto;
import com.example.demo.web.dto.PostsSaveRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
             //   .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        	 .orElse(new Posts());		
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
    
    public Page<PostsListResponseDto> findAllPaging(Pageable pageable) {
    	int pageNumber = pageable.getPageNumber() <=0 ? 0 : pageable.getPageNumber()-1;
    	int pageSize = pageable.getPageSize();
    	pageable = PageRequest.of(pageNumber, pageSize);
        return postsRepository.findAllPage(pageable);
    }
}

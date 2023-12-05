package com.example.demo.posts.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.posts.controller.PostsSaveRequestDto;
import com.example.demo.posts.domain.Posts;
import com.example.demo.posts.domain.PostsRepository;
import com.example.demo.posts.service.PostsResponseDto;
import com.example.demo.posts.service.PostsService;
import com.example.demo.posts.service.PostsUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsServiceImpl implements PostsService{

	final PostsRepository postsRepository;
	
	@Transactional
	@Override
	public Long save(PostsSaveRequestDto requestDto) {
		return postsRepository.save(requestDto.toEntity()).getId();
	}

	@Override
	public Long update(Long id, PostsUpdateRequestDto requestDto) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		posts.update(requestDto.getTitle(), requestDto.getContent());
		return id;
	}

	@Override
	public PostsResponseDto findById(Long id) {
		Posts entity = postsRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		
		return new PostsResponseDto(entity);
	}

	
}

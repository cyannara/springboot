package com.example.demo.posts.service;

import com.example.demo.posts.controller.PostsSaveRequestDto;

public interface PostsService {

	public Long save(PostsSaveRequestDto requestDto);
	public Long update(Long id,PostsUpdateRequestDto requestDto);
	public PostsResponseDto findById(Long id);
}

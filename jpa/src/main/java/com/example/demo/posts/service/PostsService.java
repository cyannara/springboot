package com.example.demo.posts.service;

import org.springframework.data.domain.Page;

import com.example.demo.posts.controller.PostsSaveRequestDto;

public interface PostsService {

	public Long save(PostsSaveRequestDto requestDto);
	public Long update(Long id,PostsUpdateRequestDto requestDto);
	public Long delete(Long id);	
	public PostsResponseDto findById(Long id);
	public Page<PostsListResponseDto> findAllDesc(int pageNo) ;
}

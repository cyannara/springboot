package com.example.demo.posts.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.posts.controller.PostsSaveRequestDto;
import com.example.demo.posts.domain.Posts;
import com.example.demo.posts.domain.PostsRepository;
import com.example.demo.posts.dto.PostsListResponseDto;
import com.example.demo.posts.dto.PostsResponseDto;
import com.example.demo.posts.dto.PostsUpdateRequestDto;
import com.example.demo.posts.service.PostsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsServiceImpl implements PostsService{

	final int PAGE_SIZE = 3;
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
		postsRepository.save(posts);
		return id;
	}
	
	@Override
	public Long delete(Long id) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		postsRepository.delete(posts);
		return id;
	}

	@Override
	public PostsResponseDto findById(Long id) {
		Posts entity = postsRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		
		return new PostsResponseDto(entity);
	}

	public Page<PostsListResponseDto> findAllDesc(int pageNo) {
		
		// Item 조회
		Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
		Page<Posts> entitys = postsRepository.findAll(pageable);

		// ItemDto로 변환
		//return entitys.stream().map(PostsListResponseDto::new).collect(Collectors.toList());
		return  entitys.map(postPage -> new PostsListResponseDto(postPage));
		  
	    //Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC));
	    //Page<PostsResponseDto> page = postsRepository.findAll(pageable).map(PostsResponseDto::from);

	}
	
}

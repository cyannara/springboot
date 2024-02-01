package com.example.demo.posts.service;

import java.time.LocalDateTime;

import com.example.demo.posts.domain.Posts;

import lombok.Getter;

@Getter
public class PostsListResponseDto {
	private Long id;
	private String title;
	private String author;
	private LocalDateTime mdoifiedDate;
	
	public PostsListResponseDto(Posts entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.author = entity.getAuthor();
		//this.modifiedDate = entity.getModifiedDate();
	}
	
}

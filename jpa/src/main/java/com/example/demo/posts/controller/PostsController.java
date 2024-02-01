package com.example.demo.posts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.posts.service.PostsResponseDto;
import com.example.demo.posts.service.PostsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PostsController {

	private final PostsService postsService;
	
	@GetMapping("/posts")
	public String findById(Model model, @RequestParam(required = false, defaultValue = "1") int page) {
		model.addAttribute("blogDetails", postsService.findAllDesc(page));
		return "posts";
	}
	
	@GetMapping("/posts/save")
	public String postsSave() {
		return "posts-save";
	}
	
	@GetMapping("/posts/update/{id}")
	public String postsUpdate(@PathVariable Long id, Model model) {
		PostsResponseDto dto = postsService.findById(id);
		model.addAttribute("post", dto);
		return "posts-update";
	}	
}

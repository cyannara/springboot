package com.example.demo.posts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.posts.dto.PostsResponseDto;
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

	/* 글 상세보기 */
//@GetMapping("/posts/read/{id}")
//public String read(@PathVariable Long id, @LoginUser UserSessionDto user, Model model) {
//PostsResponseDto dto = postsService.findById(id);
//List<CommentResponseDto> comments = dto.getComments();
///* 댓글 관련 */
//if (comments != null && !comments.isEmpty()) {
//model.addAttribute("comments", comments);
//}
///* 사용자 관련 */
//if (user != null) {
//model.addAttribute("user", user.getNickname());
///*게시글 작성자 본인인지 확인*/
//if (dto.getUserId().equals(user.getId())) {
//model.addAttribute("writer", true);
//}
//}
//postsService.updateView(id); 
//// views ++
//model.addAttribute("posts", dto);
//return "posts/posts-read";
//}    

}

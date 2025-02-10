package com.example.demo.web;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.domain.Posts.PostsResponseDto;
import com.example.demo.service.PostsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    
	@GetMapping("/")
	public String index(Model model, @PageableDefault(page = 1, size = 3, sort = "id", direction = Direction.ASC ) Pageable pagable) {
		//model.addAttribute("posts", postsService.findAllDesc() );
		model.addAttribute("posts", postsService.findAllPaging(pagable) );
		return "index";
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

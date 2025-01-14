package com.example.demo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.board.service.BoardDTO;
import com.example.demo.board.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

	private BoardService service;

	@GetMapping("/list")
	public void list(Model model) {
	  log.info("list");
	  model.addAttribute("list", service.getList());
	}

	@GetMapping("/register")
	public void registger() {}
	
	
	@PostMapping("/register")
	public String register(BoardDTO board, RedirectAttributes rttr) {
		log.info("register: " + board);		
		service.register(board);
		
		rttr.addFlashAttribute("result", true);
		return "redirect:/board/list";
	}
	
	@GetMapping({"/modify", "/get"})
	public void modify(@RequestParam(name="bno") Long bno, Model model) {
		BoardDTO board = service.get(bno);
		model.addAttribute("board", board);
	}
	
	@PostMapping("/modify")
	public String modify(BoardDTO board, RedirectAttributes rttr) {
		log.info("modify: " + board);		
		service.modify(board);
		
		rttr.addFlashAttribute("result", true);
		return "redirect:/board/list";
	}
	
	@GetMapping("/remove")
	public String remove(@RequestParam(name="bno") Long bno,
			             RedirectAttributes rttr) {
		log.info("remove: " + bno);		
		service.remove(bno);
		
		rttr.addFlashAttribute("result", true);
		return "redirect:/board/list";
	}
}

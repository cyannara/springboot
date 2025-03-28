package com.example.demo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.board.service.BoardDTO;
import com.example.demo.board.service.BoardSearchDTO;
import com.example.demo.board.service.BoardService;
import com.example.demo.common.Paging;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

	private BoardService service;

//	@GetMapping("/list")
//	public void list(Model model) {
//	  log.info("list");
//	  model.addAttribute("list", service.getList());
//	}

	@GetMapping("/list")
	public void list(Model model, BoardSearchDTO searchDTO, Paging paging) {

		// 페이징처리
		paging.setTotalRecord(service.getCount(searchDTO));
		model.addAttribute("paging", paging);
		
    //목록 조회
		searchDTO.setStart(paging.getFirst());
		searchDTO.setEnd(paging.getLast());
		model.addAttribute("list", service.getList(searchDTO));
	}

  //등록페이지
	@GetMapping("/register")
	public void registger(BoardDTO board) {
	}

  //등록처리
	@PostMapping("/register")
	public String register(@Validated BoardDTO board, 
                         BindingResult bindingResult, 
                         RedirectAttributes rttr) {
		if (bindingResult.hasErrors()) {
			return "board/register";
		}
		log.info("register: " + board);
		service.register(board);

		rttr.addFlashAttribute("result", true);
		return "redirect:/board/list";
	}

	@GetMapping({ "/modify", "/get" })
	public void modify(@RequestParam(name = "bno") Long bno, Model model) {
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
	public String remove(@RequestParam(name = "bno") Long bno, 
                       RedirectAttributes rttr) {
		log.info("remove: " + bno);
		service.remove(bno);

		rttr.addFlashAttribute("result", true);
		return "redirect:/board/list";
	}
}

package com.example.demo.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.board.service.ReplyDTO;
import com.example.demo.board.service.ReplyPageDTO;
import com.example.demo.board.service.ReplySearchDTO;
import com.example.demo.board.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/replies/")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ReplyController {
	private final ReplyService service;

	//등록처리
	@PostMapping(value = "/new", 
			     consumes = "application/json", 
			     produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> register(@RequestBody ReplyDTO vo) {

		boolean result = service.register(vo);

		return result  
				? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//수정처리
	@PutMapping( value = "/{rno}", 
			     consumes = "application/json", 
			     produces = {MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> modify(
			 @RequestBody ReplyDTO replyDTO, 
			 @PathVariable(name="rno") Long rno) {

		replyDTO.setRno(rno);

		return service.modify(replyDTO) == true 
				? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	//삭제처리
	@DeleteMapping(value = "/{rno}", 
			       produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> remove(@PathVariable(name="rno") Long rno) {

		return service.remove(rno) == true 
				? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	//단건조회
	@GetMapping("/{rno}")
	public ReplyDTO get(@PathVariable("rno") Long rno) {
		return service.get(rno);
	}

	//댓글목록조회
	@GetMapping(value = "/pages/{bno}/{page}", 
			    produces = {  MediaType.APPLICATION_XML_VALUE,
			    MediaType.APPLICATION_JSON_VALUE })
	public ReplyPageDTO getList(@PathVariable(name="page") int page, 
			                    @PathVariable(name="bno") Long bno) {

		ReplySearchDTO replySearchDTO = new ReplySearchDTO(page, 10);

		return service.getList(replySearchDTO, bno);
	}

}


package com.example.demo.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.example.demo.board.service.ReplyPageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest 
public class ReplyControllerTest {

    @Autowired MockMvc mvc;
    
    //@Test
    @DisplayName("reply 등록")
    void register() throws Exception {
    	//given
    	String requestBody = """ 
    			{
    			  "bno": 1,
    			  "reply": "댓글등록",
    			  "replyer": "이순신"
    			}
    			""";
    	
    	//when //then
    	mvc.perform(post("/replies/new")
    	    	     .content(requestBody)
    	    	     .contentType(MediaType.APPLICATION_JSON_VALUE) 
    	)
    	.andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print());
    	
    }
    
    //@Test
    @DisplayName("reply 단건 조회")
    void getreply() throws Exception {
    	
    	//given
    	Long rno = 2L;
    	String url = "/replies/"+ rno;
    	
    	//when 
    	mvc.perform(
    			get(url).
    			accept(MediaType.APPLICATION_JSON_VALUE)  
    	)
    	//then
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$.reply").value("댓글수정"))
        .andDo(print()) ;
    	
    }
    
    @Autowired ObjectMapper objectMapper;
    
    @Test
    @DisplayName("게시글의 reply 조회")
    void getreplyByBno() throws Exception {
    	
    	//given
    	Long bno = 1L;
    	int page = 1;
    	//String url = String.format("/replies/pages/%d/%d", bno, page);
    	
    	//when 
    	String responseBody = mvc.perform(
    			get("/replies/pages/{bno}/{page}",bno, page)
    			.accept(MediaType.APPLICATION_JSON_VALUE) 
    	)  	
    	.andReturn().getResponse().getContentAsString();
    	
    	//then
    	String json = objectMapper.readTree(responseBody).toPrettyString();
    	log.debug(json);
    	
    	ReplyPageDTO dto = objectMapper.readValue(responseBody, ReplyPageDTO.class);
    	
    	log.debug("replyCnt : " + dto.getReplyCnt() );
    	log.debug("first reply : " + dto.getList().get(0).getReply() );
    }
}

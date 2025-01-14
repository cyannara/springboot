package com.example.demo.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ModelMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class BoardControllerTest {

    @Autowired MockMvc mvc;
    
    @Test
    @DisplayName("조회 컨트롤러")
    void list() throws Exception {
    	ModelMap map = mvc.perform(MockMvcRequestBuilders.get("/board/list"))
    	   .andReturn()
    	   .getModelAndView()
    	   .getModelMap();
    	
    	log.debug(map.getAttribute("list").toString());   
    	log.debug(map.getAttribute("paging").toString());  
    	
    }
    
    //@Test
    @DisplayName("등록 컨트롤러")
    void register() throws Exception  {
    	String param = "title=moctest11&content=내용&writer=kim";
    	mvc.perform(post("/board/register")
    	   .content(param)
    	   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    	)
    	 //.andExpect(status().isOk())
         //.andExpect((ResultMatcher) content().string("main"))
         //.andDo(MockMvcResultHandlers.print());
         ;  
    }
}

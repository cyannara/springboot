package com.example.www.emp;



import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.www.emp.web.HelloController;

@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

	@Autowired 
	private MockMvc mvc;
	
	@Test
	public void hello가_리턴() throws Exception {
		String hello = "hello";
		mvc.perform(get("/hello"))
			.andExpect(status().isOk())
			.andExpect(content().string(hello))
			;
			
	}
	
	@Test
	public void hello_dto_리턴() throws Exception {
		String name = "hello";
		int amount =1000;
		mvc.perform(get("/hello/dto")
					.param("name", name)
					.param("amount", String.valueOf(amount)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is(name)))
			.andExpect(jsonPath("$.amount", is(amount)));
	}
}

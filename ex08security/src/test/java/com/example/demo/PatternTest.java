package com.example.demo;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

public class PatternTest {

	@Test
	@DisplayName("URI 패턴 매치")
	public void test() {
		AntPathMatcher matcher = new AntPathMatcher();
		//MockHttpServletRequest request = new MockHttpServletRequest("GET","/project/123");
		//request.setServletPath("/project/123");
		//System.out.println(request.getRequestURI());
		String pattern = "/project/*";
		String uri = "/project/new";
		assertTrue(matcher.match(pattern, uri));
	}
}

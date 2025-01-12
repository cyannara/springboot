package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MapTest {

	@Test
	public void test() {
		// List (ArrayList, LinkedList) : 순차    { 1,2,1,4,6} 
		// Set : 중복값X 
		// Map<key, Value> : 검색,   VO
		
		Map<String, Object> emp = new HashMap<>();
		emp.put("firstName", "scott");
		emp.put("lastName", "king");
		
		System.out.println(emp.get("firstName"));
	}
}

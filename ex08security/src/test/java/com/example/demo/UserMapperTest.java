package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.securing.mapper.UserMapper;
import com.example.demo.securing.service.RoleDTO;
import com.example.demo.securing.service.UserDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UserMapperTest {

	@Autowired UserMapper userMapper;
	
	@Test
	public void test() {		
		UserDTO user = userMapper.getUser("user");
		//List<RoleDTO> list = userMapper.getRole(user.getId());
		
		log.info(user.toString());
		//log.info(list.toString());
	}
}

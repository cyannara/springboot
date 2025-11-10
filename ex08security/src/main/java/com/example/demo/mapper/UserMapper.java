package com.example.demo.mapper;

import java.util.List;

import com.example.demo.service.RoleDTO;
import com.example.demo.service.UserDTO;

public interface UserMapper {
	UserDTO getUser(String loginId);
	List<RoleDTO> getRole(Long id);
}

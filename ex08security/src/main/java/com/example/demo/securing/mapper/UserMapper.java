package com.example.demo.securing.mapper;

import java.util.List;

import com.example.demo.securing.service.RoleDTO;
import com.example.demo.securing.service.UserDTO;

public interface UserMapper {
	UserDTO getUser(String loginId);
	List<RoleDTO> getRole(Long id);
}

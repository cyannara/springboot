package com.example.demo.securing.service;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	  private Long id;
	  private String loginId;
	  private String password;
	  private String  fullName;
	  private String  deptName;
	  
	  private List<RoleDTO> roles;
}

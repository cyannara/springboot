package com.example.demo.service;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserDTO implements Serializable{
	  private Long id;
	  private String loginId;
	  private String password;
	  private String  fullName;
	  private String  deptName;
	  
	  private List<RoleDTO> roles;
}

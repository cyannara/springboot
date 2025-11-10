package com.example.demo.service;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleDTO implements Serializable{
	private String userId;
	private String roleName;
}

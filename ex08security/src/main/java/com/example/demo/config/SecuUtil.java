package com.example.demo.config;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.service.CustomerUser;
import com.example.demo.service.UserDTO;

public class SecuUtil {

	public static UserDTO getUser() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getDetails();
		UserDTO userDetails = null;
		System.out.println(obj);
		//if (! obj instanceof IsAnony) {
			userDetails = ((CustomerUser)obj).getUserDTO();
		//}
		//else { 
		//	userDetails = new UserDTO();
		//}
		return userDetails;
	}
}

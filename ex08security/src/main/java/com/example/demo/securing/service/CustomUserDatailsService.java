package com.example.demo.securing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.securing.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDatailsService implements UserDetailsService {

	private final UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("userDTO: start");
		//단건조회
		UserDTO userDTO = userMapper.getUser(username);
		if(userDTO == null) {
			throw new UsernameNotFoundException("id check");
		}
		System.out.println("userDTO: "+userDTO);
		//List<GrantedAuthority> role = new ArrayList<>();
		//userDTO.getRoles().forEach(r -> role.add(new SimpleGrantedAuthority(r.getRoleName()) ));
		//return new User(userDTO.getLoginId(), userDTO.getPassword(), role );
		return new CustomerUser(userDTO);
	}

}

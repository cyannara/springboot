package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.util.AntPathMatcher;

import com.example.demo.mapper.MenuMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuService {

	 private final MenuMapper menuMapper;

	    public Menu findByUrlAndMethod(String uri, String method) {

	        List<Menu> menus = menuMapper.findByHttpMethodAndEnabled(method, "Y");

	        for (Menu menu : menus) {
	            AntPathMatcher matcher = new AntPathMatcher();
	            if (matcher.match(menu.getUrl(), uri)) {
	                return menu;
	            }
	        }

	        return null;
	    }

}

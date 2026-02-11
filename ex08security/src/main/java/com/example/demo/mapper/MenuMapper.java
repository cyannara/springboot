package com.example.demo.mapper;

import java.util.List;

import com.example.demo.service.Menu;

public interface MenuMapper {

	public List<Menu> findByHttpMethodAndEnabled(String method, String string) ;

}

package com.example.demo.ex.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.ex.mapper.Sample1Mapper;
import com.example.demo.ex.mapper.Sample2Mapper;
import com.example.demo.ex.service.SampleService;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SampleServiceImpl  implements SampleService{
	@Setter(onMethod_ = @Autowired ) 
	Sample1Mapper mapper1;
	@Setter(onMethod_ = @Autowired ) 
	Sample2Mapper  mapper2;
	
	@Override
	@Transactional
	public void addData(String value) {
		log.info("mapper1......");
		mapper1.insertCol1(value);
		
		log.info("mapper2......");
		mapper2.insertCol2(value);
		
		log.info("end......");
	}
	
	
	
	
}

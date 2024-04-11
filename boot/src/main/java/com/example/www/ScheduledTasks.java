package com.example.www;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.www.emp.service.EmpService;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired EmpService service;
	
	//@Scheduled(fixedRate = 5000)
	//@Scheduled(cron = "0 0 */1 * * *")
	public void reportCurrentTime() {
		service.getTime();
	}
}

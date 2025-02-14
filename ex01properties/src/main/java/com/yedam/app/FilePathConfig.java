package com.yedam.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilePathConfig implements WebMvcConfigurer{

	@Value("${upload.path}")
	String path;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/attach/**")
				.addResourceLocations("file://" + path);
	}
}

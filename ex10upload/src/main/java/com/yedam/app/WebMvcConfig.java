package com.yedam.app;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${file.upload.dir}")
	private String uploadDir;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 문자열을 Path 객체로 변환 -> 절대경로로 변환 -> 경로에서 불필요한 요소를 제거
		Path path = Paths.get(uploadDir).toAbsolutePath().normalize();

		// OS에 맞게 file:///c:/upload/ 또는 file:/upload/
		String resourcePath = path.toUri().toString();

		// 외부 경로에 접근할 resource 경로 지정
		// 접근방식 http://upload/파일명
		registry.addResourceHandler("/upload/**").addResourceLocations(resourcePath);
	}
}

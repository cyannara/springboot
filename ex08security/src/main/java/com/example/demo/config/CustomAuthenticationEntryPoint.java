package com.example.demo.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        if (isAjaxRequest(request)) {
            // AJAX 요청일 경우 JSON 응답
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("error", "Unauthorized");
            responseBody.put("message", "로그인이 필요합니다.");
            responseBody.put("status", HttpStatus.UNAUTHORIZED.value());

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        } else {
            // 일반 요청은 로그인 페이지로 리디렉트
            response.sendRedirect("/login");
        }
    }
    
    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        //return "XMLHttpRequest".equalsIgnoreCase(requestedWith);
        return request.getRequestURI().startsWith(request.getContextPath()+"/api");
    }
}

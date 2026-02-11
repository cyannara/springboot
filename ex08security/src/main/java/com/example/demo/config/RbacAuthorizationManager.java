package com.example.demo.config;


import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import com.example.demo.service.Menu;
import com.example.demo.service.MenuService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RbacAuthorizationManager
        implements AuthorizationManager<RequestAuthorizationContext> {

    private final MenuService menuService;

	
    @Override
    public AuthorizationDecision check(
            Supplier<Authentication> authenticationSupplier,
            RequestAuthorizationContext context) {

        Authentication authentication = authenticationSupplier.get();
        HttpServletRequest request = context.getRequest();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 1️. DB에서 URL 매칭되는 메뉴 찾기
        Menu menu = menuService.findByUrlAndMethod(uri, method);

        if (menu == null) {
            return new AuthorizationDecision(false);
        }

        // 2️. HTTP Method → Action 변환
        String action = convertMethodToAction(method);

        String requiredAuthority =
                menu.getMenuCode() + ":" + action;

        // 3️. 로그인 사용자의 Authority와 비교
        boolean granted = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(requiredAuthority));

        return new AuthorizationDecision(granted);
    }

    private String convertMethodToAction(String method) {
        return switch (method) {
            case "GET" -> "READ";
            case "POST" -> "CREATE";
            case "PUT" -> "UPDATE";
            case "DELETE" -> "DELETE";
            default -> "READ";
        };
    }


}


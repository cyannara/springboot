package com.yedam.app.member.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yedam.app.member.exception.MemberTaskException;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class TokenControllerAdvice {

  @ExceptionHandler(MemberTaskException.class)
  public ResponseEntity<Map<String, String>> handleTaskException(MemberTaskException ex) {

    log.error(ex.getMessage());

    String msg = ex.getMsg();
    int status = ex.getCode();

    Map<String, String> map = Map.of("error", msg);


    return ResponseEntity.status(status).body(map);
  }


  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception) {

    log.info("handleAccessDeniedException..................");

    Map<String, Object> errors = new HashMap<>();
    errors.put("message", exception.getMessage());

    return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
  }

}
package com.yedam.app;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/v1/samples")
public class SampleController {


  //@PreAuthorize("hasRole('ROLE_USER')")
  @GetMapping("/list")
  public ResponseEntity<?> list() {

    log.info("list............");

    String[] arr = {"AAA", "BBB", "CCC"};

    return ResponseEntity.ok(arr);

  }

}
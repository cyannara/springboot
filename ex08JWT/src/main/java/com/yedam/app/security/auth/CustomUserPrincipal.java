package com.yedam.app.security.auth;

import java.security.Principal;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CustomUserPrincipal implements Principal {

  private final String mid;

  @Override
  public String getName() {
    return mid;
  }
}

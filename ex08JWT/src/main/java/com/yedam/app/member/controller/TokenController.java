package com.yedam.app.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.member.dto.MemberDTO;
import com.yedam.app.member.service.MemberService;
import com.yedam.app.security.util.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/token")
@Log4j2
@RequiredArgsConstructor
public class TokenController {

  private final MemberService memberService;

  private final JWTUtil jwtUtil;

  @PostMapping("/make")
  public ResponseEntity<Map<String, String>> makeToken(@RequestBody MemberDTO memberDTO ) {

    log.info("make token............");

    MemberDTO memberDTOResult = memberService.read(memberDTO.getMid(), memberDTO.getMpw());

    log.info(memberDTOResult);

    String mid = memberDTOResult.getMid();

    Map<String, Object> dataMap = memberDTOResult.getDataMap();

    String accessToken = jwtUtil.createToken(dataMap, 10);

    String refreshToken = jwtUtil.createToken(Map.of("mid", mid), 60 * 24 * 7);

    log.info("accessToken: " + accessToken);
    log.info("refreshToken: " + refreshToken);


    return ResponseEntity.ok(
            Map.of("accessToken", accessToken, "refreshToken", refreshToken)
    );

  }

  @PostMapping("/refresh")
  public ResponseEntity<Map<String, String>> refreshToken(
          @RequestHeader("Authorization") String accessTokenStr,
          @RequestParam("refreshToken") String refreshToken,
          @RequestParam("mid") String mid
  ){

    log.info("access token with Bearer............" + accessTokenStr);

    if(accessTokenStr == null || !accessTokenStr.startsWith("Bearer ")) {
      return handleException("No Access Token", 400); //400 Bad Request
    }

    if(refreshToken == null) {
      return handleException("No Refresh Token", 400); //400 Bad Request
    }

    log.info("refresh token............" + refreshToken);

    if(mid == null) {
      return handleException("No Mid", 400); //400 Bad Request
    }

    //Access Token이 만료되었는지 확인
    String accessToken = accessTokenStr.substring(7);

    try{
      jwtUtil.validateToken(accessToken);

      //아직 만료 기한이 남아 있는 상황
      Map<String, String> data = makeData(mid, accessToken, refreshToken);

      log.info("Access Token is not expired..................");


      return ResponseEntity.ok(data);

    }catch(ExpiredJwtException expiredJwtException) {

      try {
        //Refresh가 필요한 상황
        Map<String, String> newTokenMap = makeNewToken(mid, refreshToken);
        return ResponseEntity.ok(newTokenMap);

      }catch(Exception e) {
        return handleException("REFRESH "+e.getMessage(), 400); //400 Bad Request
      }

    }catch(Exception e) {
      e.printStackTrace(); //디버깅용
      return handleException(e.getMessage(), 400); //400 Bad Request
    }
  }


  private Map<String, String> makeData(String mid, String accesssToken, String refreshToken){

    return Map.of("mid", mid, "accessToken", accesssToken, "refreshToken", refreshToken);
  }

  private ResponseEntity<Map<String, String>> handleException(String msg, int status) {

    return ResponseEntity.status(status).body(Map.of("error", msg));
  }


  private Map<String, String> makeNewToken(String mid, String refreshToken) {

    Map<String, Object> claims = jwtUtil.validateToken(refreshToken);

    log.info("refresh token claims: " + claims);

    if (!mid.equals(claims.get("mid").toString())) {
      throw new RuntimeException("Invalid Refresh Token Host");
    }

    // mid를 이용해서 사용자 정보를 다시 확인한 후에 새로운 토큰 생성
    MemberDTO memberDTO = memberService.getByMid(mid);

    Map<String, Object> newClaims = memberDTO.getDataMap();

    String newAccessToken = jwtUtil.createToken(newClaims, 10);

    String newRefreshToken = jwtUtil.createToken(Map.of("mid", mid), 60 * 24 * 7);

    return makeData(mid, newAccessToken, newRefreshToken);

  }


}
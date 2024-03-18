package com.hongik.ce.f23.team1.screw.global.controlleradvice;

import com.hongik.ce.f23.team1.screw.global.controlleradvice.dto.ExceptionResponse;
import com.hongik.ce.f23.team1.screw.global.exception.ScrewExceptionInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@NoArgsConstructor
@RestControllerAdvice
public class AuthExceptionHandler {


  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<ExceptionResponse> handleExpiredJwtException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ExceptionResponse.from(ScrewExceptionInfo.EXPIRED_TOKEN));
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleEmailNotFoundException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ExceptionResponse.from(ScrewExceptionInfo.USER_NOT_FOUND));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ExceptionResponse> handleWrongPasswordException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ExceptionResponse.from(ScrewExceptionInfo.WRONG_PASSWORD));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ExceptionResponse> handleAuthenticationException() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ExceptionResponse.from(ScrewExceptionInfo.REQUIRE_AUTHENTICATION));
  }
  @ExceptionHandler(JwtException.class)
  public ResponseEntity<ExceptionResponse> handleJwtException() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ExceptionResponse.from(ScrewExceptionInfo.REQUIRE_AUTHENTICATION));
  }



}

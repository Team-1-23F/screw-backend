package com.hongik.ce.f23.team1.screw.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final HandlerExceptionResolver resolver;

  public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
    this.resolver = resolver;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException // 항상 InsufficientAuthenticationException여서 의미가 없음
  ) {
    Exception exception = authException;
    if (response.getStatus() == 401 || getExceptionFromRequest(request) != null) {
      exception = getExceptionFromRequest(request);
    }

    resolver.resolveException(request, response, null, exception);
  }

  private Exception getExceptionFromRequest(HttpServletRequest request) {
    return (Exception) request.getAttribute("Exception");
  }
}

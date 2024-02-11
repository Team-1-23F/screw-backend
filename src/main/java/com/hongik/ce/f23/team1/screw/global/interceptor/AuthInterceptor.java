package com.hongik.ce.f23.team1.screw.global.interceptor;

import com.hongik.ce.f23.team1.screw.global.constant.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler
  ) throws Exception {

    if (handler instanceof HandlerMethod) {
      if (hasNoSession(request)) {
        response.sendError(401);
        return false;
      }
    }

    return true;
  }

  private boolean hasNoSession(@NonNull HttpServletRequest request) {
    final HttpSession session = request.getSession(false);

    return session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null;
  }
}

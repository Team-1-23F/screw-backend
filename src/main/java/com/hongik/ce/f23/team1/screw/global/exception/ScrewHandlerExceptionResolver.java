package com.hongik.ce.f23.team1.screw.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class ScrewHandlerExceptionResolver implements HandlerExceptionResolver {

  final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public ModelAndView resolveException(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      Object handler,
      @NonNull Exception ex
  ) {
    try {
      if (ex instanceof ScrewException) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        Map<String, Object> errorResult = new HashMap<>();
        errorResult.put("message", ex.getMessage());

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResult));

        return new ModelAndView();
      }
    } catch (IOException e) {
      return null;
    }

    return null;
  }
}

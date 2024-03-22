package com.hongik.ce.f23.team1.screw.config.security.filter;

import com.hongik.ce.f23.team1.screw.config.security.CustomUserDetails;
import com.hongik.ce.f23.team1.screw.user.domain.User;
import com.hongik.ce.f23.team1.screw.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;


  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  )
      throws ServletException, IOException {
    final String authorization = request.getHeader("Authorization");

    if (authorization == null || !authorization.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authorization.split(" ")[1];

    try {
      final Long userId = jwtUtil.getUserId(token);

      final CustomUserDetails userDetails = new CustomUserDetails(
          User
              .builder()
              .id(userId)
              .build()
      );

      final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
      );

      SecurityContextHolder
          .getContext()
          .setAuthentication(authenticationToken);
    } catch (Exception e) {
      request.setAttribute("Exception", e);
    }

    filterChain.doFilter(request, response);
  }
}

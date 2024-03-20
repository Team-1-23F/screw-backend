package com.hongik.ce.f23.team1.screw.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongik.ce.f23.team1.screw.auth.repository.RefreshTokenRepository;
import com.hongik.ce.f23.team1.screw.auth.ui.dto.LoginRequest;
import com.hongik.ce.f23.team1.screw.config.security.CustomUserDetails;
import com.hongik.ce.f23.team1.screw.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {


  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtUtil jwtUtil;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  public LoginFilter(RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.jwtUtil = jwtUtil;
  }


  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws AuthenticationException {
    final UsernamePasswordAuthenticationToken token = this.getAuthToken(request);

    return getAuthenticationManager().authenticate(token);
  }


  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      @NonNull Authentication authResult
  ) throws IOException, ServletException {
    CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();

    Long userId = userDetails.getUserId();

    String accessToken = jwtUtil.createAccessToken(userId);
    String refreshToken = jwtUtil.createRefreshToken(userId);

    setTokens(response, accessToken, refreshToken);

    refreshTokenRepository.save(userId, refreshToken);

    super.successfulAuthentication(request, response, chain, authResult);
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      @NonNull AuthenticationException failed
  ) throws ServletException, IOException {
    request.setAttribute("Exception", failed);

    super.unsuccessfulAuthentication(request, response, failed);
  }

  private UsernamePasswordAuthenticationToken
  getAuthToken(@NonNull HttpServletRequest request) {
    try {
      LoginRequest loginRequest = objectMapper.readValue(
          request.getInputStream(),
          LoginRequest.class
      );

      return new UsernamePasswordAuthenticationToken(
          loginRequest.getEmail(),
          loginRequest.getPassword(),
          List.of((GrantedAuthority) () -> CustomUserDetails.ROLE)
      );
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  private void setTokens(@NonNull HttpServletResponse response, String accessToken, String refreshToken)
      throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    PrintWriter writer = response.getWriter();
    writer.print("{\"access_token\": \"" + accessToken + "\", \"refresh_token\": \"" + refreshToken + "\"}");
    writer.flush();
    writer.close();
  }

}

  package com.hongik.ce.f23.team1.screw.global.filter;

  import com.fasterxml.jackson.databind.ObjectMapper;
  import com.hongik.ce.f23.team1.screw.auth.ui.dto.LoginRequest;
  import jakarta.servlet.FilterChain;
  import jakarta.servlet.ServletException;
  import jakarta.servlet.http.HttpServletRequest;
  import jakarta.servlet.http.HttpServletResponse;
  import java.io.IOException;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.security.authentication.AuthenticationManager;
  import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
  import org.springframework.security.core.Authentication;
  import org.springframework.security.core.AuthenticationException;
  import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
  import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


  @Slf4j
  public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginFilter(AuthenticationManager authenticationManager) {
      this.authenticationManager = authenticationManager;
      log.info("authenticationManager: {}", authenticationManager);
      // UsernamePasswordAuthenticationFilter의 기본 로그인 path '/login'을 수정
      setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
      try {
        return authenticationManager.authenticate(this.getAuthToken(request));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
        Authentication authResult) throws IOException, ServletException {
      super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException {
      super.unsuccessfulAuthentication(request, response, failed);
    }

    private UsernamePasswordAuthenticationToken getAuthToken(HttpServletRequest request) throws IOException {
      LoginRequest loginRequest = objectMapper.readValue(
          request.getInputStream(),
          LoginRequest.class
      );

      return new UsernamePasswordAuthenticationToken(
          loginRequest.getEmail(),
          loginRequest.getPassword(),
          null
      );
    }

  }

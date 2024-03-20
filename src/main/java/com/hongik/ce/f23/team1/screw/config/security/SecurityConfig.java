package com.hongik.ce.f23.team1.screw.config.security;


import com.hongik.ce.f23.team1.screw.auth.repository.RefreshTokenRepository;
import com.hongik.ce.f23.team1.screw.config.security.filter.JwtFilter;
import com.hongik.ce.f23.team1.screw.config.security.filter.LoginFilter;
import com.hongik.ce.f23.team1.screw.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

  private final AuthenticationConfiguration authenticationConfiguration;
  private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtUtil jwtUtil;
  private final RefreshTokenRepository refreshTokenRepository;


  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public JwtFilter jwtFilter() {
    return new JwtFilter(jwtUtil);
  }

  @Bean
  public LoginFilter loginFilter() throws Exception {
    LoginFilter loginFilter = new LoginFilter(refreshTokenRepository, jwtUtil);
    loginFilter.setFilterProcessesUrl("/auth/login");
    loginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());

    return loginFilter;
  }

  // TODO: 불필요한 주석 제거
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // csrf를 disable 처리
    // csrf란 브라우저에 몰래 이상한 요청을 심어두고
    // 사용자가 세션 정보를 가지는 순간 이상한 요청이 유효하게 작동하도록 하는 수작
    //
    // 활성화하면 주요 요청에 대해서 csrf 토큰을 발행하여 검증하는 로직이 추가되는데,
    // JWT 기반으로 개발할 것이기 때문에 상관 없음

    http
        .csrf(AbstractHttpConfigurer::disable);

    // form으로 로그인 안하니까 제거
    http
        .formLogin(AbstractHttpConfigurer::disable);

    // form으로 로그인 안하니까 제거
    http
        .httpBasic(AbstractHttpConfigurer::disable);

    http
        .authorizeHttpRequests(
            (auth) -> auth
                .requestMatchers(
                    "/auth/join",
                    "/auth/login",
                    "/auth/refresh"
//                    , "/error"
                ).permitAll()
                .anyRequest().authenticated()
        );

    http.addFilterAt(
        loginFilter(),
        UsernamePasswordAuthenticationFilter.class
    );

    http.addFilterBefore(
        jwtFilter(),
        LoginFilter.class
    );

    http
        .exceptionHandling(
            httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
                jwtAuthenticationEntryPoint
            )
        );

    // JWT 기반에서는 세션을 사용하지 않는다.
    // 따라서 불필요한 메모리를 소모하지 않도록 제거한다.
    http
        .sessionManagement(
            (session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

    return http.build();
  }
}

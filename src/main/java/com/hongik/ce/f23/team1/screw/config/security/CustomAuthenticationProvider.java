package com.hongik.ce.f23.team1.screw.config.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component // 컴포넌트로 등록해두니 LoginFilter가 알아서 활용한다.
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(@NonNull Authentication authentication) throws AuthenticationException {
    String email = authentication.getPrincipal().toString();
    String password = authentication.getCredentials().toString();

    CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
    }

    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  @Override
  public boolean supports(@NonNull Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}

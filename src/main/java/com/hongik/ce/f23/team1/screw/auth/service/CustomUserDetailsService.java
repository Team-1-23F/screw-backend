package com.hongik.ce.f23.team1.screw.auth.service;

import com.hongik.ce.f23.team1.screw.config.security.CustomUserDetails;
import com.hongik.ce.f23.team1.screw.user.domain.User;
import com.hongik.ce.f23.team1.screw.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(
      UserRepository userRepository
  ) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);

    return user.map(CustomUserDetails::new).orElse(null);
  }
}

package com.hongik.ce.f23.team1.screw.auth.service;

import com.hongik.ce.f23.team1.screw.auth.domain.MemberId;
import com.hongik.ce.f23.team1.screw.auth.ui.dto.LoginRequest;
import com.hongik.ce.f23.team1.screw.user.application.UserService;
import com.hongik.ce.f23.team1.screw.user.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {

  private final UserService userService;

  public AuthService(UserService userService) {
    this.userService = userService;
  }

  public MemberId login(LoginRequest loginRequest) {
    User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

    return new MemberId(user.getId());
  }
}

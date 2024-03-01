package com.hongik.ce.f23.team1.screw.auth.service;

import com.hongik.ce.f23.team1.screw.auth.domain.MemberId;
import com.hongik.ce.f23.team1.screw.auth.ui.dto.JoinRequest;
import com.hongik.ce.f23.team1.screw.auth.ui.dto.LoginRequest;
import com.hongik.ce.f23.team1.screw.user.application.UserService;
import com.hongik.ce.f23.team1.screw.user.domain.User;
import com.hongik.ce.f23.team1.screw.user.domain.User.LoginMethod;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserService userService;

  public AuthService(
      BCryptPasswordEncoder bCryptPasswordEncoder,
      UserService userService
  ) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userService = userService;
  }

  public MemberId login(LoginRequest loginRequest) {
    User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

    return new MemberId(user.getId());
  }

  public void join(JoinRequest joinRequest) {
    userService.signUp(
        joinRequest.toUserEntity(LoginMethod.PASSWORD),
        bCryptPasswordEncoder.encode(joinRequest.getPassword())
    );
  }
}

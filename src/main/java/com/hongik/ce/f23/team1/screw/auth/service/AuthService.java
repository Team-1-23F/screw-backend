package com.hongik.ce.f23.team1.screw.auth.service;

import com.hongik.ce.f23.team1.screw.auth.repository.RefreshTokenRepository;
import com.hongik.ce.f23.team1.screw.auth.ui.dto.JoinRequest;
import com.hongik.ce.f23.team1.screw.global.exception.ScrewException;
import com.hongik.ce.f23.team1.screw.global.exception.ScrewExceptionInfo;
import com.hongik.ce.f23.team1.screw.user.application.UserService;
import com.hongik.ce.f23.team1.screw.user.domain.User.LoginMethod;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Transactional
public class AuthService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserService userService;


  public void join(JoinRequest joinRequest) {
    userService.signUp(
        joinRequest.toUserEntity(LoginMethod.PASSWORD),
        bCryptPasswordEncoder.encode(joinRequest.getPassword())
    );
  }

  public boolean checkRefreshToken(Long userId, String refreshToken) {
    boolean isRegistered = refreshTokenRepository.exist(userId, refreshToken);

    if (isRegistered) {
      return true;
    }

    throw new ScrewException(ScrewExceptionInfo.EXPIRED_TOKEN);
  }

  public void removeRefreshToken(Long userId) {
    refreshTokenRepository.remove(userId);
  }
}

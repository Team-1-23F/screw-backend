package com.hongik.ce.f23.team1.screw.auth.repository;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {

  final Map<Long, String> validRefreshToken = new HashMap<>();

  public void save(Long userId, String refreshToken) {

    validRefreshToken.put(userId, refreshToken);
  }

  public boolean exist(Long userId, String refreshToken) {
    String token = validRefreshToken.get(userId);

    return token != null && token.equals(refreshToken);
  }

  public void remove(Long userId) {
    validRefreshToken.remove(userId);
  }
}

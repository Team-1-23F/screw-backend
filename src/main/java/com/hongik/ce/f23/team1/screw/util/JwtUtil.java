package com.hongik.ce.f23.team1.screw.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {
  static final String kUserId = "userId";

  private final SecretKey secretKey;


  // @Value: Prop에서 값을 가져오기 위한 annotation
  public JwtUtil(@NonNull @Value("${spring.jwt.secret}") String secret) {
    this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().getAlgorithm());
  }

  public Long getUserId(String token) {

    return Jwts
        .parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get(kUserId, Long.class);
  }

  public String createJwtToken(Long userId, Long expiredMs) {
    return Jwts.builder()
        .claim(kUserId, userId)
        .expiration(new Date(System.currentTimeMillis() + expiredMs))
        .signWith(secretKey, SIG.HS256)
        .compact();
  }
}

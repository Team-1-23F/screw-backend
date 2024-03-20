package com.hongik.ce.f23.team1.screw.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
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
  static final String kAccess = "access";
  static final String kRefresh = "refresh";


  public static final Long ACCESS_TOKEN_EXP = 10 * 60 * 1000L; // 10분
  public static final Long REFRESH_TOKEN_EXP = 3 * 24 * 60 * 60 * 1000L; // 3일

  private final SecretKey accessSecretKey;


  // @Value: Prop에서 값을 가져오기 위한 annotation
  public JwtUtil(@NonNull @Value("${spring.jwt.secret}") String accessSecret) {
    this.accessSecretKey = new SecretKeySpec(accessSecret.getBytes(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().getAlgorithm());
  }

  public Long getUserId(String token) {
    return Jwts
        .parser()
        .verifyWith(accessSecretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get(kUserId, Long.class);
  }

  public Long getUserIdFromRefreshToken(String token) {
    Claims claims = Jwts
        .parser()
        .verifyWith(accessSecretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();

    if (!claims.getSubject().equals("Refresh")) {
      throw new JwtException("");
    }

    return Jwts
        .parser()
        .verifyWith(accessSecretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get(kUserId, Long.class);
  }

  public String createAccessToken(Long userId) {
    return Jwts.builder()
        .subject(kAccess)
        .claim(kUserId, userId)
        .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXP))
        .signWith(accessSecretKey, SIG.HS256)
        .compact();
  }

  public String createRefreshToken(Long userId) {
    return Jwts.builder()
        .subject(kRefresh)
        .claim(kUserId, userId)
        .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP))
        .signWith(accessSecretKey, SIG.HS256)
        .compact();
  }
}

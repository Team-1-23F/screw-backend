package com.hongik.ce.f23.team1.screw.auth.ui.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class TokenResponse {

  @NotEmpty
  private String accessToken;

  @NotEmpty
  private String refreshToken;
}

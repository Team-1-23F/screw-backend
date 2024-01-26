package com.hongik.ce.f23.team1.screw.auth.ui.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class LoginRequest {

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  private String password;


}

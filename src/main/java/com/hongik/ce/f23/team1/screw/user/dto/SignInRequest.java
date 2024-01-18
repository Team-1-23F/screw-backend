package com.hongik.ce.f23.team1.screw.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class SignInRequest {

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  private String password;


  public SignInRequest(
      String email,
      String password
  ) {
    this.email = email;
    this.password = password;
  }

}

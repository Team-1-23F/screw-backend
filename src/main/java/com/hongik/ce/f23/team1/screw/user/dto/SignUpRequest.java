package com.hongik.ce.f23.team1.screw.user.dto;


import com.hongik.ce.f23.team1.screw.user.domain.User;
import com.hongik.ce.f23.team1.screw.user.domain.User.LoginMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SignUpRequest {

  @NotNull
  private String email;
  @NotNull
  private String password;
  private String nickname;
  private String job;

  public SignUpRequest(
      String email,
      String password,
      String nickname,
      @Nullable String job
  ) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.job = job;
  }

  public User toUserEntity(LoginMethod loginMethod) {
    return User.builder()
        .email(email)
        .nickname(nickname)
        .job(job)
        .loginMethod(loginMethod)
        .build();
  }
}

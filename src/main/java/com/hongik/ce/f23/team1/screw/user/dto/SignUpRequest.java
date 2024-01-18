package com.hongik.ce.f23.team1.screw.user.dto;


import com.hongik.ce.f23.team1.screw.user.domain.User;
import com.hongik.ce.f23.team1.screw.user.domain.User.LoginMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class SignUpRequest {

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  private String password;

  @NotEmpty
  @Size(min = 2, max = 20)
  private String nickname;

  // TODO: Job을 Enum으로 분리 필요
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

package com.hongik.ce.f23.team1.screw.user.domain;

import com.hongik.ce.f23.team1.screw.global.domain.AuditableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(
    of = {"id"},
    callSuper = true
)
@ToString(of = {"id", "email", "nickname", "job", "loginMethod"})
public class User extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false, unique = true)
  private String nickname;

  @Column()
  private String job;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private LoginMethod loginMethod;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Password password;


  public enum LoginMethod {
    PASSWORD, GOOGLE
  }

  @Builder
  public User(
      Long id,
      String email,
      String nickname,
      @Nullable
      String job,
      LoginMethod loginMethod
  ) {
    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.job = job;
    this.loginMethod = loginMethod;
  }
}

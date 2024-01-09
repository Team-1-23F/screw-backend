package com.hongik.ce.f23.team1.screw.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString()
public class Password {

  @Column(nullable = false)
  private String password;

  @Id
  @OneToOne()
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;


  @Builder
  public Password(User user, String password) {
    this.user = user;
    this.password = password;
  }
}

package com.hongik.ce.f23.team1.screw.crew.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Embeddable
@Data
@Setter
public class CrewOption {

  @Column(nullable = false)
  private int maximumMember;

  @Builder
  public CrewOption(
      int maximumMember
  ) {
    this.maximumMember = maximumMember;
  }
}

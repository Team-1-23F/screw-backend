package com.hongik.ce.f23.team1.screw.crew.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
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

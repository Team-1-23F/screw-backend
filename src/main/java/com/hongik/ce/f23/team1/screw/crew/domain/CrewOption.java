package com.hongik.ce.f23.team1.screw.crew.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CrewOption {

  @Column(nullable = false)
  private int maximumMember;

}

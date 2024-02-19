package com.hongik.ce.f23.team1.screw.routine.domain;

import com.hongik.ce.f23.team1.screw.crew.domain.CrewMember;
import com.hongik.ce.f23.team1.screw.global.domain.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class RoutineProof extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(nullable = false)
  private String description;

  @Column()
  private String imgUrl;


  @ManyToOne
  private CrewMember crewMember;

  @ManyToOne
  private Routine routine;
}

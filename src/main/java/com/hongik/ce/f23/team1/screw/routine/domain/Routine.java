package com.hongik.ce.f23.team1.screw.routine.domain;

import com.hongik.ce.f23.team1.screw.crew.domain.Crew;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Routine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  private Crew crew;

  @OneToMany(
      mappedBy = "routine",
      fetch = FetchType.LAZY
  )
  private List<RoutineProof> routineProofs;
}

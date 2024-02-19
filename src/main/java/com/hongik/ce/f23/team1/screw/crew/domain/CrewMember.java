package com.hongik.ce.f23.team1.screw.crew.domain;

import com.hongik.ce.f23.team1.screw.routine.domain.RoutineProof;
import com.hongik.ce.f23.team1.screw.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "crew_member")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrewMember {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long point;

  @Column(nullable = false)
  private boolean isCreator;

  @CreatedDate
  private LocalDateTime joinedAt;

  @ManyToOne
  private User user;

  @ManyToOne
  private Crew crew;

  @OneToMany(
      mappedBy = "crewMember",
      fetch = FetchType.LAZY
  )
  private List<RoutineProof> routineProofs;

}

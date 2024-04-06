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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "crew_member")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public class CrewMember {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long point;

  @Column(nullable = false)
  private Boolean isCreator;

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

  @Builder
  public CrewMember(
      Long id,
      Long point,
      boolean isCreator,
      User user,
      Crew crew,
      List<RoutineProof> routineProofs
  ) {
    this.id = id;
    this.point = point;
    this.isCreator = isCreator;
    this.user = user;
    this.crew = crew;
    this.routineProofs = routineProofs;
  }
}

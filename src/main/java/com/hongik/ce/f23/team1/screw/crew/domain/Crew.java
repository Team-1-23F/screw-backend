package com.hongik.ce.f23.team1.screw.crew.domain;

import com.hongik.ce.f23.team1.screw.global.domain.AuditableEntity;
import com.hongik.ce.f23.team1.screw.routine.domain.Routine;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public class Crew extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Embedded
  private CrewOption crewOption;

  @OneToMany(
      mappedBy = "crew",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<CrewMember> crewMembers;

  @OneToMany(
      mappedBy = "crew",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Routine> routines;

  @ManyToMany
  @JoinTable(
      name = "crew_tag",
      joinColumns = @JoinColumn(name = "crew_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private List<Tag> tags;

  @Builder
  public Crew(
      Long id,
      String name,
      String description,
      CrewOption crewOption,
      List<CrewMember> crewMembers,
      List<Routine> routines,
      List<Tag> tags
      ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.crewOption = crewOption;
    this.crewMembers =crewMembers;
    this.routines = routines;
    this.tags = tags;
  }
}

package com.hongik.ce.f23.team1.screw.crew.ui.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;


@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class CreateCrewRequest {

  @NotEmpty
  @Size(min = 2, max = 20)
  private String name;

  @NotEmpty
  @Size(max = 100)
  private String description;

  @NotNull
  @Range(min = 1, max = 8)
  private int maximumMember;

  @NotNull
  private List<@Size(min = 1, max = 10) String> tags;
}

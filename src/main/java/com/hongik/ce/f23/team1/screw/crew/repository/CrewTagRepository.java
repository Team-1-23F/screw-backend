package com.hongik.ce.f23.team1.screw.crew.repository;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class CrewTagRepository {

  private final CrewTagMapper crewTagMapper;

  public void saveAll(Long crewId, List<Long> tagIds) {
    crewTagMapper.saveAll(crewId, tagIds);
  }
}

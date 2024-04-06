package com.hongik.ce.f23.team1.screw.crew.repository;

import com.hongik.ce.f23.team1.screw.crew.domain.Crew;
import com.hongik.ce.f23.team1.screw.crew.repository.mapper.CrewMapper;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class CrewRepository {

  private final CrewMapper crewMapper;


  public void save(Crew crew) {
    crewMapper.save(crew);
  }

  public List<Crew> findAll() {
    return crewMapper.findAll();
  }

  public Optional<Crew> findById(Long id) {
    log.debug("~~~~b: {}", id);

    return crewMapper.findById(id);
  }
}

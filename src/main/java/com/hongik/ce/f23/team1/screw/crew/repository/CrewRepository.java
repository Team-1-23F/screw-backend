package com.hongik.ce.f23.team1.screw.crew.repository;

import com.hongik.ce.f23.team1.screw.crew.domain.Crew;
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


  public Optional<Crew> findById(Long id) {
    return crewMapper.findById(id);
  }

  public List<Crew> findAllWithTag() {
    return crewMapper.findAllWithTag();
  }
}
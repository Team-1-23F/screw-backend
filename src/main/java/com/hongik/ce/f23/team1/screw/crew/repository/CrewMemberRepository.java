package com.hongik.ce.f23.team1.screw.crew.repository;

import com.hongik.ce.f23.team1.screw.crew.domain.CrewMember;
import com.hongik.ce.f23.team1.screw.crew.repository.mapper.CrewMemberMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class CrewMemberRepository {

  private final CrewMemberMapper crewMemberMapper;


  public void save(CrewMember crewMember) {
    crewMemberMapper.save(crewMember);
  }


}

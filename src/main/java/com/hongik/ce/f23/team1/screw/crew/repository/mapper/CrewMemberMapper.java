package com.hongik.ce.f23.team1.screw.crew.repository.mapper;

import com.hongik.ce.f23.team1.screw.crew.domain.CrewMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrewMemberMapper {

  void save(CrewMember crewMember);


}

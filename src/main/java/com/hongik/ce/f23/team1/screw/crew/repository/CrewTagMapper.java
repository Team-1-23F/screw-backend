package com.hongik.ce.f23.team1.screw.crew.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CrewTagMapper {

  void saveAll(@Param("crewId") Long crewId, @Param("tagIds") List<Long> tagIds);
}

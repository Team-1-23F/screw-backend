package com.hongik.ce.f23.team1.screw.crew.repository;

import com.hongik.ce.f23.team1.screw.crew.domain.Crew;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrewMapper {

  void save(Crew crew);


  // MyBatis Mapper 인터페이스의 메서드중에서 파라미터가 2개가 넘는 경우에는 @Param 어노테이션을 추가하자
  Optional<Crew> findById(Long id);

  List<Crew> findAllWithTag();

}

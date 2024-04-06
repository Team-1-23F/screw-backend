package com.hongik.ce.f23.team1.screw.crew.repository.mapper;

import com.hongik.ce.f23.team1.screw.crew.domain.Tag;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
  void saveAll(List<Tag> tags);

  List<Tag> findByNames(List<String> tagNames);
}

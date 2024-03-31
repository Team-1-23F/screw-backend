package com.hongik.ce.f23.team1.screw.crew.repository;

import com.hongik.ce.f23.team1.screw.crew.domain.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class TagRepository {
  private final TagMapper tagMapper;

  public void saveAll(List<Tag> tags) {
    tagMapper.saveAll(tags);
  }

  public List<Tag> findByNames(List<String> tagNames) {
    return tagMapper.findByNames(tagNames);
  }
}

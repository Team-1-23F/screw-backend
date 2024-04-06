package com.hongik.ce.f23.team1.screw.crew.application;

import com.hongik.ce.f23.team1.screw.crew.domain.Crew;
import com.hongik.ce.f23.team1.screw.crew.domain.CrewMember;
import com.hongik.ce.f23.team1.screw.crew.domain.CrewOption;
import com.hongik.ce.f23.team1.screw.crew.domain.Tag;
import com.hongik.ce.f23.team1.screw.crew.repository.CrewMemberRepository;
import com.hongik.ce.f23.team1.screw.crew.repository.CrewRepository;
import com.hongik.ce.f23.team1.screw.crew.repository.CrewTagRepository;
import com.hongik.ce.f23.team1.screw.crew.repository.TagRepository;
import com.hongik.ce.f23.team1.screw.crew.ui.dto.CreateCrewRequest;
import com.hongik.ce.f23.team1.screw.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewService {

  private final CrewRepository crewRepository;
  private final TagRepository tagRepository;
  private final CrewTagRepository crewTagRepository;
  private final CrewMemberRepository crewMemberRepository;


  @Transactional
  public void create(CreateCrewRequest createCrewRequest, User creator) {
    // Tag 생성
    List<Tag> tags = createTags(createCrewRequest.getTags());

    // Crew 생성
    Crew newCrew = Crew
        .builder()
        .name(createCrewRequest.getName())
        .description(createCrewRequest.getDescription())
        .crewOption(
            CrewOption
                .builder()
                .maximumMember(createCrewRequest.getMaximumMember())
                .build()
        )
        .build();

    crewRepository.save(newCrew);
    crewTagRepository.saveAll(
        newCrew.getId(),
        tags.stream().map(Tag::getId).toList()
    );

    CrewMember crewMember = CrewMember.builder()
        .point(0L)
        .isCreator(true)
        .user(creator)
        .crew(newCrew)
        .build();

    crewMemberRepository.save(crewMember);
  }

  public List<Crew> getAll() {
    return crewRepository.findAll();
  }

  private List<Tag> createTags(List<String> tagNames) {
    final List<Tag> tags = tagNames
        .stream()
        .map((tag) -> Tag.builder()
            .name(tag)
            .build()
        )
        .toList();

    tagRepository.saveAll(tags);
    return tags;
  }


}

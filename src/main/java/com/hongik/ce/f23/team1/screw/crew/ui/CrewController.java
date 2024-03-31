package com.hongik.ce.f23.team1.screw.crew.ui;

import com.hongik.ce.f23.team1.screw.config.security.CustomUserDetails;
import com.hongik.ce.f23.team1.screw.crew.application.CrewService;
import com.hongik.ce.f23.team1.screw.crew.ui.dto.CreateCrewRequest;
import com.hongik.ce.f23.team1.screw.global.exception.ScrewException;
import com.hongik.ce.f23.team1.screw.global.exception.ScrewExceptionInfo;
import com.hongik.ce.f23.team1.screw.user.application.UserService;
import com.hongik.ce.f23.team1.screw.user.domain.User;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/crew")
@RequiredArgsConstructor
public class CrewController {

  private final UserService userService;
  private final CrewService crewService;

  @PostMapping
  ResponseEntity<Void> create(@Valid @RequestBody CreateCrewRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    // 내 정보 불러오기
    final Optional<User> user = userService.findById(userDetails.getUserId());

    if (user.isEmpty()) {
      throw new ScrewException(ScrewExceptionInfo.USER_NOT_FOUND);
    }

    crewService.createCrew(request, user.get());

    return ResponseEntity.ok().build();
  }
}

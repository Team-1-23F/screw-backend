package com.hongik.ce.f23.team1.screw.auth.ui;

import com.hongik.ce.f23.team1.screw.auth.domain.MemberId;
import com.hongik.ce.f23.team1.screw.auth.service.AuthService;
import com.hongik.ce.f23.team1.screw.auth.ui.dto.LoginRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

  private final AuthService authService;

  AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/join")
  void join() {
  }

  @PostMapping("/login")
  ResponseEntity<Void> login(
      @Valid @RequestBody LoginRequest loginRequest,
      HttpSession session
  ) {
    try {
      MemberId memberId = authService.login(loginRequest);

      registerSession(session, memberId);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok().build();
  }


  @PostMapping("/logout")
  ResponseEntity<Void> logout(HttpSession session) {
    if (session != null) {
      session.invalidate();
    }

    return ResponseEntity.ok().build();
  }

  private void registerSession(HttpSession session, MemberId memberId) {
    // TODO: 세션의 attribute 이름 상수화하기
    session.setAttribute("LOGIN_USER", memberId);
    // TODO: 글로벌로 설정하는 것이 더 나을 수 있겠지만 일단 다음과 같이 설정
    session.setMaxInactiveInterval(1800);
  }
}

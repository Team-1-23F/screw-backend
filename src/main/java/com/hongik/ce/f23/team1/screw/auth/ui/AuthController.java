package com.hongik.ce.f23.team1.screw.auth.ui;


import com.hongik.ce.f23.team1.screw.auth.service.AuthService;
import com.hongik.ce.f23.team1.screw.auth.ui.dto.JoinRequest;
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
  ResponseEntity<Void> join(@Valid @RequestBody JoinRequest joinRequest) {
    authService.join(joinRequest);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/logout")
  ResponseEntity<Void> logout() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return ResponseEntity.ok().build();
  }
}

package com.hongik.ce.f23.team1.screw.user.api;

import com.hongik.ce.f23.team1.screw.global.exception.ExceptionResponse;
import com.hongik.ce.f23.team1.screw.user.application.UserService;
import com.hongik.ce.f23.team1.screw.user.dto.SignInRequest;
import com.hongik.ce.f23.team1.screw.user.dto.SignUpRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(
      @Valid @RequestBody SignUpRequest signUpRequest,
      BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(
              new ExceptionResponse("잘못된 요청입니다.")
          );
    }

    userService.signUp(signUpRequest);

    return ResponseEntity.ok(null);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(
      @Valid @RequestBody SignInRequest signInRequest,
      BindingResult bindingResult
  ) {
    userService.signIn(signInRequest);

    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(
              new ExceptionResponse("잘못된 요청입니다.")
          );
    }

    return ResponseEntity.ok(userService.signIn(signInRequest));
  }
}

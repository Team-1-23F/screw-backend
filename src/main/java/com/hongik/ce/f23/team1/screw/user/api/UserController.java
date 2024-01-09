package com.hongik.ce.f23.team1.screw.user.api;

import com.hongik.ce.f23.team1.screw.user.application.UserService;
import com.hongik.ce.f23.team1.screw.user.dto.SignUpRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/sign-up")
  public SignUpRequest signUp(@RequestBody @Valid SignUpRequest signUpRequest) {

    userService.signUp(signUpRequest);
    return signUpRequest;
  }

  @PostMapping("/sign-in")
  public String signIn(@ModelAttribute String x) {
    return "sign-up";
  }
}

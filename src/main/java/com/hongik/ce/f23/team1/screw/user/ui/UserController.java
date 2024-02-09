package com.hongik.ce.f23.team1.screw.user.ui;

import com.hongik.ce.f23.team1.screw.user.application.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

  public UserController(UserService userService) {
  }


}

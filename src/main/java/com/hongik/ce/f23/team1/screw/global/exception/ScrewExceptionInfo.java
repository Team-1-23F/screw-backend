package com.hongik.ce.f23.team1.screw.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScrewExceptionInfo {

  DUPLICATED_EMAIL("1", "이미 사용중인 이메일입니다."),
  DUPLICATED_NICKNAME("2", "이미 사용중인 닉네임입니다."),

  EXPIRED_TOKEN("3", "토큰이 만료되었습니다."),
  USER_NOT_FOUND("4", "해당 사용자가 존재하지 않습니다."),
  WRONG_PASSWORD("5", "비밀번호가 일치하지 않습니다."),
  REQUIRE_AUTHENTICATION("6", "인증이 필요합니다."),

  NOT_FOUND_ERROR("-1", "존재하지 않는 주소입니다."),
  UNKNOWN_ERROR("0", "알 수 없는 에러 발생");


  private final String code;
  private final String message;
}

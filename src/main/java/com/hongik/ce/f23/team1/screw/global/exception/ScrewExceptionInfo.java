package com.hongik.ce.f23.team1.screw.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScrewExceptionInfo {
  DUPLICATED_EMAIL("1", "이미 사용중인 이메일입니다."),
  DUPLICATED_NICKNAME("2", "이미 사용중인 닉네임입니다."),
  // TODO: 추후 지워야함
  TMP("0", "temporary message");


  private final String code;
  private final String message;
}

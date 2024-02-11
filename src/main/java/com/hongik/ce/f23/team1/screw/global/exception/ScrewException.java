package com.hongik.ce.f23.team1.screw.global.exception;

import lombok.Getter;

@Getter
public class ScrewException extends RuntimeException {


  private final ScrewExceptionInfo exceptionInfo;


  public ScrewException(ScrewExceptionInfo exceptionInfo) {
    super();
    this.exceptionInfo = exceptionInfo;
  }
}

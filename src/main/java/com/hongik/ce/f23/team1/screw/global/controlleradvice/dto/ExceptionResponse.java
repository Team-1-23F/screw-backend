package com.hongik.ce.f23.team1.screw.global.controlleradvice.dto;

import com.hongik.ce.f23.team1.screw.global.exception.ScrewExceptionInfo;

public record ExceptionResponse(String code, String errorMessage) {

  public static ExceptionResponse from(ScrewExceptionInfo exceptionInfo) {
    return new ExceptionResponse(exceptionInfo.getCode(), exceptionInfo.getMessage());
  }
}

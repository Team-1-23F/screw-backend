package com.hongik.ce.f23.team1.screw.global.controlleradvice;

import com.hongik.ce.f23.team1.screw.global.controlleradvice.dto.ExceptionResponse;
import com.hongik.ce.f23.team1.screw.global.exception.ScrewException;
import com.hongik.ce.f23.team1.screw.global.exception.ScrewExceptionInfo;
import java.util.EnumMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class ScrewExceptionHandler {

  private final EnumMap<ScrewExceptionInfo, HttpStatus> httpStatusMap = new EnumMap<>(ScrewExceptionInfo.class);

  private ScrewExceptionHandler() {
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleScrewException(ScrewException ex) {

    return ResponseEntity.status(getStatusCode(ex))
        .body(ExceptionResponse.from(ex.getExceptionInfo()));
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoResourceFoundException.class)
  public void handleNotFoundException() {
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ExceptionResponse> handleException() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ExceptionResponse.from(ScrewExceptionInfo.UNCATCHABLE_ERROR));
  }

  private HttpStatus getStatusCode(ScrewException ex) {
    return httpStatusMap.getOrDefault(ex.getExceptionInfo(), HttpStatus.BAD_REQUEST);
  }
}

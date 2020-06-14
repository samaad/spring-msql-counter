package org.demo.assign.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler(GenericException.class)
  public ResponseEntity handleException(GenericException e) {
    return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
  }
}

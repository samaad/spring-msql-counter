package org.demo.assign.exception;

import org.springframework.http.HttpStatus;


public class GenericException extends Exception {

  private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
  public GenericException(String message, Throwable cause) {
    super(message, cause);
    this.httpStatus = httpStatus;
  }
}

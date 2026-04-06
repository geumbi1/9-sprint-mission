package com.sprint.mission.discodeit.exception.user;

public class InvalidCredentialsException extends UserException {

  public InvalidCredentialsException() {
    super(ErrorCode.INVALID_USER_CREDENTIALS);
  }

  public static InvalidCredentialsException wrongPassword() {
    InvalidCredentialsException exception = new InvalidCredentialsException();
    return exception;
  }
} 
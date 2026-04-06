package com.sprint.mission.discodeit.exception.userstatus;

public class UserStatusException extends DiscodeitException {

  public UserStatusException(ErrorCode errorCode) {
    super(errorCode);
  }

  public UserStatusException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
} 
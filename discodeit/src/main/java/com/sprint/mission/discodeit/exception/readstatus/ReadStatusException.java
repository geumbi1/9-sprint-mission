package com.sprint.mission.discodeit.exception.readstatus;

public class ReadStatusException extends DiscodeitException {

  public ReadStatusException(ErrorCode errorCode) {
    super(errorCode);
  }

  public ReadStatusException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
} 
package com.sprint.mission.discodeit.exception.message;

public class MessageException extends DiscodeitException {

  public MessageException(ErrorCode errorCode) {
    super(errorCode);
  }

  public MessageException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
} 
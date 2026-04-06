package com.sprint.mission.discodeit.exception.binarycontent;

public class BinaryContentException extends DiscodeitException {

  public BinaryContentException(ErrorCode errorCode) {
    super(errorCode);
  }

  public BinaryContentException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
} 
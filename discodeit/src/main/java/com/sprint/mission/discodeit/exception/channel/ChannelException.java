package com.sprint.mission.discodeit.exception.channel;

public class ChannelException extends DiscodeitException {

  public ChannelException(ErrorCode errorCode) {
    super(errorCode);
  }

  public ChannelException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
} 
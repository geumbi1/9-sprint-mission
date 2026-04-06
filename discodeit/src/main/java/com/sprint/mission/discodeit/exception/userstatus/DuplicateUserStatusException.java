package com.sprint.mission.discodeit.exception.userstatus;

import java.util.UUID;

public class DuplicateUserStatusException extends UserStatusException {

  public DuplicateUserStatusException() {
    super(ErrorCode.DUPLICATE_USER_STATUS);
  }

  public static DuplicateUserStatusException withUserId(UUID userId) {
    DuplicateUserStatusException exception = new DuplicateUserStatusException();
    exception.addDetail("userId", userId);
    return exception;
  }
} 
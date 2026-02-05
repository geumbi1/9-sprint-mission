package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.UserStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public record UserResponse(
        UUID id,//유저 고유번호를 보여줄 건 아니지만 로직을 짤 때 필요하니까
        String username,
        String email,
        UserStatus userStatus
) {}//사용자가 정보를 보겠다고 할때 (보안상)보여줄 수 있는 것만 보여줄려고 이 클래스 필요


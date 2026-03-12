package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.UserStatusDto;
import com.sprint.mission.discodeit.dto.request.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.jpa.UserRepository;
import com.sprint.mission.discodeit.repository.jpa.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class BasicUserStatusService implements UserStatusService {

  private final UserStatusRepository userStatusRepository;
  private final UserRepository userRepository;
  private final UserStatusMapper userStatusMapper;

  @Override
  public UserStatusDto create(UserStatusCreateRequest request) {

    UUID userId = request.userId();

    User user = userRepository.findById(userId)
        .orElseThrow(
            () -> new NoSuchElementException(
                "User with id " + userId + " does not exist"
            )
        );

    if (userStatusRepository.findByUserId(userId).isPresent()) {
      throw new IllegalArgumentException(
          "UserStatus with userId " + userId + " already exists"
      );
    }

    UserStatus userStatus =
        new UserStatus(user, request.lastActiveAt());

    UserStatus saved = userStatusRepository.save(userStatus);
    return userStatusMapper.toDto(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public UserStatusDto find(UUID userStatusId) {

    UserStatus userStatus = userStatusRepository.findById(userStatusId)
        .orElseThrow(() -> new NoSuchElementException(
            "UserStatus with id " + userStatusId + " not found"
        ));

    return userStatusMapper.toDto(userStatus);

  }

  @Override
  @Transactional(readOnly = true)
  public List<UserStatusDto> findAll() {

    return userStatusRepository.findAll()
        .stream()
        .map(userStatusMapper::toDto)
        .toList();
  }

  @Override
  public UserStatusDto update(UUID userStatusId, UserStatusUpdateRequest request) {

    UserStatus userStatus = userStatusRepository.findById(userStatusId)
        .orElseThrow(
            () -> new NoSuchElementException(
                "UserStatus with id " + userStatusId + " not found"
            )
        );

    userStatus.update(request.newLastActiveAt());

    return userStatusMapper.toDto(userStatus);
  }

  @Override
  public UserStatusDto updateByUserId(UUID userId, UserStatusUpdateRequest request) {

    UserStatus userStatus = userStatusRepository.findByUserId(userId)
        .orElseThrow(
            () -> new NoSuchElementException(
                "UserStatus with userId " + userId + " not found"
            )
        );

    userStatus.update(request.newLastActiveAt());

    return userStatusMapper.toDto(userStatus);
  }

  @Override
  public void delete(UUID userStatusId) {

    UserStatus userStatus = userStatusRepository.findById(userStatusId)
        .orElseThrow(
            () -> new NoSuchElementException(
                "UserStatus with id " + userStatusId + " not found"
            )
        );

    userStatusRepository.delete(userStatus);
  }
}
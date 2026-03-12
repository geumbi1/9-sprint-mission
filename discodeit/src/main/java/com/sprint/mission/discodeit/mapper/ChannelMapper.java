package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.jpa.MessageRepository;
import com.sprint.mission.discodeit.repository.jpa.ReadStatusRepository;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelMapper {

  private final MessageRepository messageRepository;
  private final ReadStatusRepository readStatusRepository;

  public ChannelDto toDto(Channel channel) {

    // 마지막 메시지 시간 조회 (최신 메시지 1개만 가져오기)
    Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "createdAt"));
    Instant lastMessageAt = messageRepository.findAllByChannelId(channel.getId(), pageable)
        .stream()
        .findFirst() // 최신 메시지 1개
        .map(Message::getCreatedAt)
        .orElse(null);

    // 채널 참여자 조회
    List<UUID> participantIds = readStatusRepository.findAllByChannelId(channel.getId())
        .stream()
        .map(status -> status.getUser().getId())
        .toList();

    return new ChannelDto(
        channel.getId(),
        channel.getType(),
        channel.getName(),
        channel.getDescription(),
        participantIds,
        lastMessageAt
    );
  }
}
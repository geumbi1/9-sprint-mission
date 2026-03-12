package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.data.MessageDto;
import com.sprint.mission.discodeit.entity.Message;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

  public MessageDto toDto(Message message) {

    if (message == null) {
      return null;
    }

    return new MessageDto(
        message.getId(),
        message.getContent(),
        message.getChannel().getId(),
        message.getAuthor().getId(),
        message.getAttachments()
            .stream()
            .map(binaryContent -> binaryContent.getId())
            .toList(), message.getCreatedAt(),
        message.getUpdatedAt()
    );
  }

  public List<MessageDto> toDtoList(List<Message> messages) {

    if (messages == null) {
      return List.of();
    }

    return messages.stream()
        .map(this::toDto)
        .toList();
  }
}
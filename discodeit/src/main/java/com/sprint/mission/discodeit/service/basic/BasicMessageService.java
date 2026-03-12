package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.MessageDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.mapper.PageResponseMapper;
import com.sprint.mission.discodeit.repository.jpa.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.jpa.ChannelRepository;
import com.sprint.mission.discodeit.repository.jpa.MessageRepository;
import com.sprint.mission.discodeit.repository.jpa.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class BasicMessageService implements MessageService {

  private final BinaryContentStorage binaryContentStorage;
  private final MessageRepository messageRepository;
  private final MessageMapper messageMapper;
  private final ChannelRepository channelRepository;
  private final UserRepository userRepository;
  private final BinaryContentRepository binaryContentRepository;

  @Override
  public MessageDto create(MessageCreateRequest messageCreateRequest,
      List<BinaryContentCreateRequest> binaryContentCreateRequests) {

    UUID channelId = messageCreateRequest.channelId();
    UUID authorId = messageCreateRequest.authorId();

    if (!channelRepository.existsById(channelId)) {
      throw new NoSuchElementException("Channel with id " + channelId + " does not exist");
    }

    if (!userRepository.existsById(authorId)) {
      throw new NoSuchElementException("Author with id " + authorId + " does not exist");
    }

    List<UUID> attachmentIds = binaryContentCreateRequests.stream()
        .map(attachmentRequest -> {
          BinaryContent binaryContent = new BinaryContent(
              attachmentRequest.fileName(),
              (long) attachmentRequest.bytes().length,
              attachmentRequest.contentType()
          );

          BinaryContent saved = binaryContentRepository.save(binaryContent);

          binaryContentStorage.put(saved.getId(), attachmentRequest.bytes());

          return saved.getId();
        })
        .toList();

    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(() ->
            new NoSuchElementException("Channel with id " + channelId + " does not exist"));

    User author = userRepository.findById(authorId)
        .orElseThrow(() ->
            new NoSuchElementException("Author with id " + authorId + " does not exist"));

    Message message = new Message(
        messageCreateRequest.content(),
        channel,
        author
    );

    Message saved = messageRepository.save(message);

    return messageMapper.toDto(saved);
  }

  @Override
  public MessageDto find(UUID messageId) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(() ->
            new NoSuchElementException("Message with id " + messageId + " not found"));

    return messageMapper.toDto(message);
  }

  @Override
  public PageResponse<MessageDto> findAllByChannelId(UUID channelId, Pageable pageable) {
    Slice<Message> slice = messageRepository.findAllByChannelId(channelId, pageable);
    Slice<MessageDto> dtoSlice = slice.map(messageMapper::toDto);
    return PageResponseMapper.fromSlice(dtoSlice);
  }

  @Override
  public MessageDto update(UUID messageId, MessageUpdateRequest request) {

    Message message = messageRepository.findById(messageId)
        .orElseThrow(() ->
            new NoSuchElementException("Message with id " + messageId + " not found"));

    message.update(request.newContent());

    Message updated = messageRepository.save(message);

    return messageMapper.toDto(updated);
  }

  @Override
  public void delete(UUID messageId) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(
            () -> new NoSuchElementException("Message with id " + messageId + " not found"));

    message.getAttachments()
        .forEach(att -> binaryContentRepository.deleteById(att.getId()));
    messageRepository.deleteById(messageId);
  }
}

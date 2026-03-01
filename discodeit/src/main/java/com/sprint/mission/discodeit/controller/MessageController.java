package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.controller.api.MessageApi;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.MessageService;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController implements MessageApi {

  private final MessageService messageService;
  private final BinaryContentService binaryContentService;

  @Override
  @PostMapping(
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
  )
  public ResponseEntity<Message> create(
      @RequestPart("messageCreateRequest") MessageCreateRequest messageCreateRequest,
      @RequestPart(value = "attachments", required = false) List<MultipartFile> Requests
  ) {
    List<BinaryContentCreateRequest> attachmentIds = binaryContentCreateRequest(Requests);
    Message message = messageService.create(messageCreateRequest, attachmentIds);
    return ResponseEntity.status(HttpStatus.CREATED).body(message);
  }

  @Override
  @GetMapping
  public ResponseEntity<List<Message>> findByChannelId(
      @RequestParam UUID channelId
  ) {
    List<Message> messageList = messageService.findAllByChannelId(channelId);
    return ResponseEntity.ok(messageList);
  }

  @Override
  @PutMapping("/{messageId}")
  public ResponseEntity<Message> update(
      @PathVariable UUID messageId,
      @RequestBody MessageUpdateRequest messageUpdateRequest
  ) {
    Message updateMessage = messageService.update(messageId, messageUpdateRequest);
    return ResponseEntity.ok(updateMessage);
  }

  @Override
  @DeleteMapping("/{messageId}")
  public ResponseEntity<Void> delete(
      @PathVariable UUID messageId
  ) {
    messageService.delete(messageId);
    return ResponseEntity.noContent().build();
  }


  public List<BinaryContentCreateRequest> binaryContentCreateRequest(
      List<MultipartFile> attachmentIds) {
    if (attachmentIds == null || attachmentIds.isEmpty()) {
      return List.of();
    }
    return attachmentIds.stream()
        .map(file -> {
          try {
            return new BinaryContentCreateRequest(

                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
            );
          } catch (IOException e) {
            throw new RuntimeException("파일 생성 중 오류발생", e);
          }
        }).toList();


  }
}

package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.ChannelApi;
import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channels")
public class ChannelController implements ChannelApi {

  private final ChannelService channelService;

  @Override
  @PostMapping(
      path = "/public"
  )
  public ResponseEntity<Channel> createPublic(
      @RequestBody PublicChannelCreateRequest publicChannelCreateRequest
  ) {
    Channel publicChannel = channelService.create(publicChannelCreateRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(publicChannel);
  }

  @Override
  @PostMapping(
      path = "/private"
  )
  public ResponseEntity<Channel> createPrivate(
      @RequestBody PrivateChannelCreateRequest privateChannelCreateRequest
  ) {
    Channel privateChannel = channelService.create(privateChannelCreateRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(privateChannel);
  }

  //    @RequestMapping(
//
//            method = RequestMethod.GET
//    )
//    public ResponseEntity<ChannelDto> find(
//            @RequestParam UUID channelId
//            ){
//        ChannelDto findChannel = channelService.find(channelId);
//        return ResponseEntity.ok(findChannel);
//    }
  @Override
  @GetMapping()
  public ResponseEntity<List<ChannelDto>> findAll(
      @RequestParam UUID userId
  ) {
    List<ChannelDto> allChannel = channelService.findAllByUserId(userId);
    return ResponseEntity.ok(allChannel);
  }

  @Override
  @PutMapping("/{channelId}")
  public ResponseEntity<Channel> update(
      @PathVariable UUID channelId,
      @RequestBody PublicChannelUpdateRequest request

  ) {
    Channel updateChannel = channelService.update(channelId, request);
    return ResponseEntity.ok(updateChannel);
  }

  @Override
  @DeleteMapping("/{channelId}")
  public ResponseEntity<Void> delete(
      @PathVariable UUID channelId
  ) {
    channelService.delete(channelId);
    return ResponseEntity.noContent().build();
  }
}

package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.jpa.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class BasicBinaryContentService implements BinaryContentService {

  private final BinaryContentRepository binaryContentRepository;
  private final BinaryContentMapper binaryContentMapper;
  private final BinaryContentStorage binaryContentStorage;

  @Override
  public BinaryContentDto create(BinaryContentCreateRequest request) {

    BinaryContent binaryContent = new BinaryContent(
        request.fileName(),
        (long) request.bytes().length,
        request.contentType()
    );
    BinaryContent saved = binaryContentRepository.save(binaryContent);
    binaryContentStorage.put(saved.getId(), request.bytes());

    return binaryContentMapper.toDto(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public BinaryContentDto find(UUID binaryContentId) {

    BinaryContent binaryContent = binaryContentRepository.findById(binaryContentId)
        .orElseThrow(
            () -> new NoSuchElementException(
                "BinaryContent with id " + binaryContentId + " not found"
            )
        );

    return binaryContentMapper.toDto(binaryContent);
  }

  @Override
  @Transactional(readOnly = true)
  public List<BinaryContentDto> findAllByIdIn(List<UUID> binaryContentIds) {

    return binaryContentRepository.findAllById(binaryContentIds)
        .stream()
        .map(binaryContentMapper::toDto)
        .toList();
  }

  @Override
  public void delete(UUID binaryContentId) {

    BinaryContent binaryContent = binaryContentRepository.findById(binaryContentId)
        .orElseThrow(
            () -> new NoSuchElementException(
                "BinaryContent with id " + binaryContentId + " not found"
            )
        );

    binaryContentRepository.delete(binaryContent);
  }
}
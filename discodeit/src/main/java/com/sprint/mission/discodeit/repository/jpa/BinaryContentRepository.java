package com.sprint.mission.discodeit.repository.jpa;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jpa")
@Repository
public interface JpaBinaryContentRepository
    extends JpaRepository<BinaryContent, UUID>, BinaryContentRepository {

  @Override
  List<BinaryContent> findAllByIdIn(List<UUID> ids);

  @Override
  Optional<BinaryContent> findById(UUID id);

  @Override
  boolean existsById(UUID id);

}
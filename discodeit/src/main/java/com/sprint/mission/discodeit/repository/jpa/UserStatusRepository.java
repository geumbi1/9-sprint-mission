package com.sprint.mission.discodeit.repository.jpa;

import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jpa")
@Repository
public interface JpaUserStatusRepository extends UserStatusRepository {

}
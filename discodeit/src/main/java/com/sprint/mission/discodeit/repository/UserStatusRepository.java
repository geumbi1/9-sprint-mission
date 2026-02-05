package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusRepository {
    UserStatus save(UserStatus userStatus);//userId만 받는게 아니고 userStatus상태 전체 객체 하나를 받아야함.
    List<UserStatus> findAll();
    Optional<UserStatus> findById(UUID id);//없는 것을 찾으면 오류나니까 옵셔널로.
    void deleteById(UUID id);//UserStatus delete~ 이렇게 했었는데 delete는 삭제하고 나서 리턴값이 불필요하니까 void delete임.
    boolean existsById(UUID id); //존재여부니까 반환타입 userStatus아니고 불리안임.


}

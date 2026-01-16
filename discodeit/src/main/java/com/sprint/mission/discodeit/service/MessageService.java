package main.java.com.sprint.mission.discodeit.service;

import main.java.com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    //생성, 읽기, 모두 읽기, 수정, 삭제
    //메시지를 생성한다. -> 내용이 들어가고 시간과 이름이 표시된다.

    Message create(String msgText, UUID senderId, UUID channelId); //누가, 어디서, 어떤 내용을 생성했는지
    Message findId(UUID id);  //메시지클래스에서 메시지마다 id를 달았으니까 그거를 찾으면 됨
    List<Message> findAll(); //모든 메시지 조회
    Message update(UUID id,String msgText);
    void delete(UUID id);

}

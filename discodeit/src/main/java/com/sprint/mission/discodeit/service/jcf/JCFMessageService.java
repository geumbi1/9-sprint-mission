package main.java.com.sprint.mission.discodeit.service.jcf;

import main.java.com.sprint.mission.discodeit.entity.Message;
import main.java.com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessageService implements MessageService {
    public List<Message> data = new ArrayList<>(); //모든 메시지를 담는 data


    @Override
    public Message create(String msgText, UUID senderId, UUID channelId) {
        Message message = new Message(msgText, senderId,channelId);
        data.add(message);

        return message;
    }


    @Override
    public Message findId(UUID id) {
        return null;
    }

    @Override
    public List<Message> findAll() {
        return List.of();
    }

    @Override
    public Message update(UUID id, String msgText) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}

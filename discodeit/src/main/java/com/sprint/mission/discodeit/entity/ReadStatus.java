package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter //객체를 private로 감쌌으니까 외부에서 쓸려고. 원래는 getUserId()등을 써서 외부에서도 이 객체를 쓸 수 있게 했는데 지금은 @getter만 하면 됨.
//사용자별 각 채널에 읽지 않은 메시지를 확인하기 위해 활용합니다.
public class ReadStatus implements Serializable {//객체를 보따리에 싸서 어디로 보낼거니까
    private static final long serialVersionUID = 1L;
    private  UUID id;
    private UUID userId;
    private UUID channelId;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastReadAt;
    //누가 읽고, 어디를 읽고, 마지막으로 읽은 시간은 ?

    public ReadStatus(UUID userId, UUID channelId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.channelId = channelId;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.lastReadAt = now;
    }

    //그럼 "읽었다"를 어떻게 확인하지-> 그 대화창에 접속하면
    public void updateLastReadAt() {
        //이 메서드가 실행되면 마지막으로 읽은 시간이 현재 시간으로 업뎃
        //메시지를 읽기만 하면 lastReadAt가 현재 시간으로 바뀌면서 기존 시간을 바꾸는거니까 updatedAt도 변경돼야함.
        Instant now = Instant.now();
        this.lastReadAt = now;
        this.updatedAt = now;

    }
}

package main.java.com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.UUID;

// "id"의 의미가 사용자가 로그인할 때 그 id가 아니고 모든 것, 하나하나에 식별번호를 달 때의 그것. 유저의 사용자 정보도 고유해야하고
// 메시지의 모든것(대화내용 등)도 고유해야지 메시지의 "안녕"을 삭제할려면 그 "안녕"의 정확한 고유번호를 알아야하니까.
// 메시지 A (ID: aaa-111, 보낸사람: 101, 내용: "안녕?")

public class Message { //누가, 어디서, 언제 , 무엇을
    private final UUID id; //여기서 id는 메시지의 고유번호를 말함
    private final UUID senderId;
    private final UUID channelId;
    private final Long createdAt; //user의 createdAt는 사용자 등록의 시간이고 여기서는 메시지 생성 날짜임
    private Long updatedAt;
    private String msgText; //메시지 내용
    private String displayName;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //자바가 원래 가지고 있는 클래스


    public Message (String msgText,UUID senderId , UUID channelId) {//그냥 UUID id가 아니고, 메시지는 보내는사람 받는사람이 있으니까 ) rn
        this.id = UUID.randomUUID();
        this.senderId = senderId;
        this.channelId = channelId;
        Long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
        this.msgText = msgText;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getMsgText() {
        return msgText;
    }

    public String getDisplayName() {
        return displayName;
    }

    //처음에는 if문으로 updateMsg가 null아닌지 를 적었는데, 유효성 검사(내용이 비어있지는 않은지, 기존과 똑같은건지)는 다른 곳에서 처리하고 여기서는 순수하게 update만.
    public void update(UUID id , String updateMsg) { //여기서 메시지를 업데이트
        this.msgText = updateMsg;
        this.updatedAt = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Message{" +
                ", msgText='" + msgText + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}

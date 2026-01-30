package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

//사용자의 프로필 이미지, 메시지에 첨부된 파일을 저장하기 위해 활용합니다.
//컴퓨터는 사진 그대로를 못 읽어서 0,1로 나타내야함(바이트)
@Getter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID userId;
    private UUID messageId;
    private Instant createdAt;
    private Instant updatedAt;
    //파일에 대한 정보들
    private byte[] bytes ;//파일의 0,1 정보를 담음. 크기가 매우 작으니까 타입은 byte
    private String contentType; //파일의 형태
    private long size;

    public BinaryContent(UUID userId, UUID messageId , byte[] bytes, String contentType){
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.messageId = messageId;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.bytes = bytes;
        this.contentType = contentType;
        this.size = (long) bytes.length;//size를 매개변수로 받는 것보다 입력받은 bytes를 length(자동 저울)를 이용해 더 정확하게 구할 수 있음.

    }
}

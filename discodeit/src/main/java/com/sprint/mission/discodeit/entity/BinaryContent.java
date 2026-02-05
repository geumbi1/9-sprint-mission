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
    private Instant createdAt;
    private Instant updatedAt;
    //파일에 대한 정보들
    private String fileName;
    private byte[] bytes ;//파일의 0,1 정보를 담음. 크기가 매우 작으니까 타입은 byte
    private String contentType; //파일의 형태
    private Long size;

    public BinaryContent(String fileName, Long size, String contentType, byte[] bytes) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        //
        this.fileName = fileName;
        this.size = size;
        this.contentType = contentType;
        this.bytes = bytes;
    }
}

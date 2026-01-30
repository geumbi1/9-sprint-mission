package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@Getter
//사용자의 온라인 상태를 확인하기 위해 활용합니다.
public class UserStatus {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID userId;
    private Instant createdAt; //instant는 기계가 쉽게 계산할 수 있는 타임스탬프 클래스
    private Instant updatedAt;
    private Instant lastSeenAt;

    UserStatus(UUID userId) {
        this.userId = userId;
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.lastSeenAt = Instant.now();
    }

    //불리안을 통해 온라인인지 아닌지
    public boolean isOnline(){
    //isAfter가 인자보다 미래일 때 true를 리턴함. 이걸로 if문보다 간결하게 가능
        //마지막 접속시간을 받아와야함-> last~.
        Instant now =  Instant.now();
        //minus는 instant안에 있는 메서드
        return lastSeenAt.isAfter(now.minus(5, ChronoUnit.MINUTES)); //true 이면 온라인 상태
    }

    //업데이트는 매일 학교에 올 때마다 출석도장을 찍어주는 일
    //사용자가 앱을 켜거나 메시지를 보낼때마다 이 메서드를 실행해서 "1초 전에도 활동했다"를 통해서 isonline()을
    //쓰면 됨. 메시지를 보내거나 접속 등 활동을 하면 이 메소드를 호출하면서 lastSeenAt를 계속 현재 시간으로 업뎃함
    public void updateLastSeenAt(){
        Instant now =  Instant.now();
        this.lastSeenAt = now;
        this.updatedAt = now;
    }

}

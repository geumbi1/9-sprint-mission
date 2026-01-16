package main.java.com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class User {
    private final UUID id;
    private final Long createdAt; //lone은 메모리에 데이터값을 직접 저장, Long은 주소값을 저장해 객체를 참조함
    private Long updatedAt; //아직 값을 할당하는게 아니니까 주소값만 알려줄 수 있도록 Long을 사용
    private String displayName;
    private String email;
    private String phoneNumber;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //자바가 원래 가지고 있는 클래스


    public User (String displayName, String email, String phoneNumber) {
        this.id = UUID.randomUUID();
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UUID getId() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public String getDisplayName() { //메소드를 실행하고 값만 돌려주면 되니까 파라미터 필요없음
        //등록할려는 정보가 담긴 곳에 접근한다
        //정보들 중에서 이름 정보에 접근한다.
        // 이름 정보 반환한다.

        // 이 위에 대한 내용은 여기서 말고 다른 곳에서 코드 적어야함
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {return phoneNumber;}

    //void string update 라고 했었는데 안 돌려줄거면 void, 문자열을 돌려줄거면 string . 둘중에 하나만 써야함
    public void update(String name, String email, String phoneNumber) {//업데이트 가능한 내용은 이름, 메일
        if(name != null) {  //수정할 때 실수로 이름을 빼먹고 보내면 기존의 이름이 사라지니까 안전장치이다.
            this.displayName = name;
        }
        if(email != null) {
            this.email = email;
        }
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
        this.updatedAt = System.currentTimeMillis();
    }


    //아래는 다건조회의 출력을 도와주는 메소드
    @Override //toString이라는 원래 자바 메소드가 있으니까 오버라이드로 재정의
    public String toString() { //객체가 참조형들이니까 주소값을 가리키고 있어서 toString을 안 쓰면 id에 들어있는값은 주소값이라 주소값을 출력함. 이걸쓰면 id의 주소값에 들어있는 내용을 출력함.
        return "User{" +
                "id=" + id +
                ", createdAt=" + sdf.format(new Date(createdAt)) + //위에서 sdf객체 만들고 날짜를 이애하기 쉽게 표현하기 위해 현재 createdAt값을 바꿔줌
                ", updatedAt=" + sdf.format(new Date(updatedAt)) +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
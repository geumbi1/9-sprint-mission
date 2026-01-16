package main.java.com.sprint.mission.discodeit;

import main.java.com.sprint.mission.discodeit.entity.Channel;
import main.java.com.sprint.mission.discodeit.entity.Message;
import main.java.com.sprint.mission.discodeit.entity.User;
import main.java.com.sprint.mission.discodeit.service.ChannelService;
import main.java.com.sprint.mission.discodeit.service.MessageService;
import main.java.com.sprint.mission.discodeit.service.UserService;
import main.java.com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import main.java.com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import main.java.com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.util.List;
import java.util.UUID;

public class JavaApplication  {
    public static void main(String[] args) {
        UserService userService = new JCFUserService();
        ChannelService channelService = new JCFChannelService();
        MessageService messageService = new JCFMessageService();



        User user1 = userService.create("홍길동", "abc123@gmail.com","010-1111-1111");
        User user2 = userService.create("김민지", "dfw455@gmail.com","010-2222-2222");

        System.out.println("유저 생성) 유저 이름: " + user1.getDisplayName() + ", 유저 이메일 : " + user1.getEmail() + ", 유저 전화번호 : " + user1.getPhoneNumber());

        List<User> users = userService.findAll();
        System.out.println("다건 조회 : " + users);

        User findUser1  = userService.findId(user1.getId());
        System.out.println("단건 조회(user1) : " + findUser1);

        User findUser2  = userService.findId(user2.getId());
        System.out.println("단건 조회(user2) : " + findUser2);

        //유저1의 정보 바꾸기
        User update1 = userService.update(user1.getId(),"홍사람","ppp6@naver.com", "010-6543-6543");
        System.out.println("수정된 정보 : " + update1);
        if(update1 != null) { //update1에 값이 잘 들어가면
            System.out.println("수정된 유저 : " + update1.getDisplayName());
        }
        else {
            System.out.println("없는 유저입니다.");
        }

        userService.delete(user1.getId());
        System.out.println("유저 삭제 완료");

        try {
            userService.findId(user1.getId());
        }catch (IllegalArgumentException e) {//e는 나중에 에러메시지 뜨는 것
            System.out.println("삭제된 유저입니다.");
        }
        System.out.println("**********************************");
        Message message = messageService.create("안녕", user1.getId(), UUID.randomUUID());

        System.out.println("메시지 내용 : " + message);

        //**********************************
        Channel channel = channelService.create("기본 채널", "기본 채널 설명 :  ..");
        System.out.println("채널 : " + channel);

        Channel channel1 = channelService.findId(channel.getId());
        System.out.println("채널 조회 : " + channel1.getChannelName());

        List<Channel> channels = channelService.findAll();
        System.out.println("채널 전체 조회 : " + channels);

        Channel channel1Update = channelService.update(channel1.getId(),"기본 채널(업뎃이름)","기본 채널 설명(업뎃설명)");
        System.out.println("채널 이름 및 설명 업데이트 : " + channel1Update);

        channelService.delete(channel1.getId());
        try {
            channelService.findId(user1.getId());
        }catch (IllegalArgumentException e) {//e는 나중에 에러메시지 뜨는 것
            System.out.println("삭제된 채널입니다.");
        }





    }
}

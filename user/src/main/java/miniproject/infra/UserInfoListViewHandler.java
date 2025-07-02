package miniproject.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class UserInfoListViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private UserInfoListRepository userInfoListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRegistered_then_CREATE_1(@Payload Registered registered) {
        try {
            if (!registered.validate()) return;

            // view 객체 생성
            UserInfoList userInfoList = new UserInfoList();
            // view 객체에 이벤트의 Value 를 set 함
            userInfoList.setUserId(registered.getUserId());
            userInfoList.setEmail(registered.getEmail());
            userInfoList.setNickname(registered.getNickname());
            // view 레파지 토리에 save
            userInfoListRepository.save(userInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}

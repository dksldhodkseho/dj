package miniproject.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    UserRepository userRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    /**
     * [이벤트 수신] writer 서비스에서 WriterApproved 이벤트 발생 시
     * 해당 사용자의 역할을 'WRITER'로 변경합니다.
     * @param writerApproved
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='WriterApproved'"
    )
    public void wheneverWriterApproved_BecomeWriter(
        @Payload WriterApproved writerApproved
    ) {
        try {
            if (!writerApproved.validate()) return;
            
            System.out.println(
                "\n\n##### listener BecomeWriter : " + writerApproved.toJson() + "\n\n"
            );

            // 1. 이벤트에 포함된 writerId를 사용하여 User Aggregate를 조회합니다.
            //    (writer 서비스의 writerId는 user 서비스의 userId와 같습니다)
            userRepository.findById(writerApproved.getWriterId()).ifPresent(user -> {
                
                // 2. User Aggregate에 미리 만들어둔 becomeWriter() 메서드를 호출합니다.
                user.becomeWriter();
                
                // 3. 변경된 User의 상태(role='WRITER')를 DB에 저장합니다.
                userRepository.save(user);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     // --- 여기에 '작가 거절됨' 이벤트 리스너를 추가합니다 ---
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='WriterRejected'"
    )
    public void wheneverWriterRejected_NotifyUser(@Payload WriterRejected writerRejected) {
        if (!writerRejected.validate()) return;
        
        System.out.println(
            "\n\n##### listener NotifyUserOfRejection : " + writerRejected.toJson() + "\n\n"
        );
        
        // 여기에 사용자에게 "작가 신청이 거절되었습니다."라고 알리는 로직을 구현합니다.
        // 예를 들어, 알림 서비스로 이벤트를 보내거나, 사용자의 상태를 변경할 수 있습니다.
        // 현재는 콘솔에 로그만 출력합니다.
        userRepository.findById(writerRejected.getWriterId()).ifPresent(user -> {
            System.out.println("알림 발송 to " + user.getNickname() + ": 작가 신청이 거절되었습니다.");
        });
    }
}
//>>> Clean Arch / Inbound Adaptor

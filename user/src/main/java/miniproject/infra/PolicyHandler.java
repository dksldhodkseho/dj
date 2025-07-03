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
}
//>>> Clean Arch / Inbound Adaptor

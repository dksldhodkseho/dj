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
    OpenAiRepository openAiRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    /**
     * [수정된 메서드]
     * book 서비스에서 표지 생성 요청 이벤트가 오면,
     * OpenAi Aggregate의 API 호출 메서드를 실행합니다.
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CoverGenerationRequested'"
    )
    public void wheneverCoverGenerationRequested_BookCoverCreate( // 메서드 이름도 역할에 맞게 변경하는 것을 추천합니다.
        @Payload CoverGenerationRequested coverGenerationRequested
    ) {
        try {
            if (!coverGenerationRequested.validate()) return;
            System.out.println(
                "\n\n##### listener BookCoverCreate: " +
                coverGenerationRequested.toJson() +
                "\n\n"
            );

            // --- 로직 구현 ---
            // 1. 이벤트 데이터를 Command 객체로 변환하여 Aggregate에 전달할 준비를 합니다.
            BookCoverCreateCommand command = new BookCoverCreateCommand();
            command.setBookId(coverGenerationRequested.getBookId());
            command.setTitle(coverGenerationRequested.getTitle());
            command.setContent(coverGenerationRequested.getContent());

            // 2. 새로운 OpenAi Aggregate 인스턴스를 생성합니다. (API 요청 1건에 해당)
            OpenAi openAi = new OpenAi();

            // 3. 인스턴스의 bookCoverCreate 메서드를 호출하여 실제 API 통신 로직을 실행합니다.
            openAi.bookCoverCreate(command);
            
            // 4. 이 API 요청 기록을 DB에 저장합니다.
            openAiRepository.save(openAi);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//>>> Clean Arch / Inbound Adaptor

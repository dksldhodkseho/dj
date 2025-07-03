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
    BookRepository bookRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PubApproved'"
    )
    public void wheneverPubApproved_PublishComplete(
        @Payload PubApproved pubApproved
    ) {
        try {
            if (!pubApproved.validate()) return;

            System.out.println(
                "\n\n##### listener PubApproved -> PublishComplete : " +
                pubApproved.toJson() +
                "\n\n"
            );

            // 1. 이벤트에 담겨온 bookId로 Book Aggregate를 조회합니다.
            bookRepository.findById(pubApproved.getBookId()).ifPresent(book -> {
                
                // 2. Book Aggregate에 만들어둔 publishComplete() 메서드를 호출합니다.
                book.publishComplete();
                
                // 3. 변경된 상태를 DB에 저장합니다. (이때 PublishCompleted 이벤트가 발행됩니다)
                bookRepository.save(book);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CoverCreated'"
    )
    public void wheneverCoverCreated_CoverCandidatesReady(
        @Payload CoverCreated coverCreated
    ) {
        CoverCreated event = coverCreated;
        System.out.println(
            "\n\n##### listener CoverCandidatesReady : " + coverCreated + "\n\n"
        );

        // Sample Logic //
        Book.coverCandidatesReady(event);
    }

    
}
//>>> Clean Arch / Inbound Adaptor

package miniproject.infra;

import javax.transaction.Transactional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    BookRepository bookRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // '출간 승인' 관련 리스너
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
            bookRepository
                .findById(pubApproved.getBookId())
                .ifPresent(book -> {
                    book.publishComplete();
                    bookRepository.save(book);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // [성공 경로 1] 구독자라서 열람이 허가된 경우 (BookAccessGranted 이벤트 수신)
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookAccessGranted'"
    )
    public void wheneverBookAccessGranted_IncrementViewCount(
        @Payload BookAccessGranted bookAccessGranted
    ) {
        try {
            if (!bookAccessGranted.validate()) return;
            System.out.println(
                "##### listener BookAccessGranted: " + bookAccessGranted.toJson()
            );

            bookRepository
                .findById(bookAccessGranted.getBookId())
                .ifPresent(book -> {
                    book.incrementViewCount();
                    bookRepository.save(book);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // [성공 경로 2] 포인트 차감에 성공하여 열람이 허가된 경우 (PointDeducted 이벤트 수신)
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointDeducted'"
    )
    public void wheneverPointDeducted_IncrementViewCount(
        @Payload PointDeducted pointDeducted
    ) {
        try {
            if (!pointDeducted.validate()) return;
            System.out.println(
                "##### listener PointDeducted: " + pointDeducted.toJson()
            );

            bookRepository
                .findById(pointDeducted.getBookId())
                .ifPresent(book -> {
                    book.incrementViewCount();
                    bookRepository.save(book);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // [실패 경로] 포인트가 부족하여 열람이 거절된 경우 (PointDeductFailed 이벤트 수신)
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointDeductFailed'"
    )
    public void wheneverPointDeductFailed_Notify(
        @Payload PointDeductFailed pointDeductFailed
    ) {
        try {
            if (!pointDeductFailed.validate()) return;
            System.out.println(
                "\n\n##### 도서 열람 실패 (포인트 부족) - 사용자 ID: " +
                pointDeductFailed.getUserId() +
                ", 도서 ID: " +
                pointDeductFailed.getBookId() +
                " #####\n\n"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 'AI 표지 생성' 관련 리스너
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CoverCreated'"
    )
    public void wheneverCoverCreated_CoverCandidatesReady(
        @Payload CoverCreated coverCreated
    ) {
        System.out.println(
            "\n\n##### listener CoverCandidatesReady : " + coverCreated + "\n\n"
        );
        // Sample Logic
    }
}
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

    // [수정 안 함] 이전에 구현한 '출간 승인 완료' 리스너
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
    
    // --- [추가] '조건부 도서 열람' 결과를 처리하는 리스너 3개 ---

    /**
     * [성공 경로 1] 구독자라서 열람이 허가된 경우 (BookAccessGranted 이벤트 수신)
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookAccessGranted'"
    )
    public void wheneverBookAccessGranted_IncrementViewCount(
        @Payload BookAccessGranted bookAccessGranted
    ) {
        try {
            if (!bookAccessGranted.validate()) return;
            System.out.println("##### listener BookAccessGranted: " + bookAccessGranted.toJson());

            // bookId로 책을 찾아 조회수 증가 로직을 실행합니다.
            bookRepository
                .findById(bookAccessGranted.getBookId())
                .ifPresent(book -> {
                    book.incrementViewCount(); // Aggregate의 조회수 증가 메서드 호출
                    bookRepository.save(book);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * [성공 경로 2] 포인트 차감에 성공하여 열람이 허가된 경우 (PointDeducted 이벤트 수신)
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointDeducted'"
    )
    public void wheneverPointDeducted_IncrementViewCount(
        @Payload PointDeducted pointDeducted
    ) {
        try {
            if (!pointDeducted.validate()) return;
            System.out.println("##### listener PointDeducted: " + pointDeducted.toJson());

            // bookId로 책을 찾아 조회수 증가 로직을 실행합니다.
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

    /**
     * [실패 경로] 포인트가 부족하여 열람이 거절된 경우 (PointDeductFailed 이벤트 수신)
     */
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
            // 실제 서비스에서는 여기서 알림 서비스로 "포인트 부족" 이벤트를 보내는 등의 로직이 들어갑니다.
            // 현재는 콘솔에 로그만 출력합니다.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- 리스너 추가 끝 ---


    // [수정 안 함] 다른 기능의 리스너
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
        // Book.coverCandidatesReady(event);
    }
}
//>>> Clean Arch / Inbound Adaptor

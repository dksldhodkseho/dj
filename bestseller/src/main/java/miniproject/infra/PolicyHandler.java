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
    BestSellerRepository bestSellerRepository;
    BookViewCountRepository bookViewCountRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookAccessGranted'"
    )
    public void wheneverBookAccessGranted_ViewCount(
        @Payload BookAccessGranted bookAccessGranted
    ) {
        BookAccessGranted event = bookAccessGranted;
        System.out.println(
            "\n\n##### listener ViewCount : " + bookAccessGranted + "\n\n"
        );

        // Sample Logic //
        BestSeller.viewCount(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointDeducted'"
    )
    public void wheneverPointDeducted_ViewCount(
        @Payload PointDeducted pointDeducted
    ) {
        PointDeducted event = pointDeducted;
        System.out.println(
            "\n\n##### listener ViewCount : " + pointDeducted + "\n\n"
        );

        // Sample Logic //
        BestSeller.viewCount(event);
    }

     @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='BookViewed'")
    public void wheneverBookViewed_IncreaseCount(@Payload BookViewed bookViewed) {
        if (!bookViewed.validate()) return;
        
        // bookId로 기존 조회수 데이터를 찾거나, 없으면 새로 생성
        BookViewCount bookViewCount = bookViewCountRepository.findById(bookViewed.getBookId())
            .orElseGet(() -> {
                BookViewCount newBook = new BookViewCount();
                newBook.setBookId(bookViewed.getBookId());
                //newBook.setTitle(bookViewed.getTitle()); // 이벤트에 title이 있다고 가정
                newBook.setViewCount(0L);
                return newBook;
            });
        
        // 조회수 업데이트
        bookViewCount.setViewCount(Long.valueOf(bookViewed.getViewCount()));
        bookViewCountRepository.save(bookViewCount);
    }
}
//>>> Clean Arch / Inbound Adaptor

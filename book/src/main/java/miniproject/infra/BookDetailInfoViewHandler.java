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
public class BookDetailInfoViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private BookDetailInfoRepository bookDetailInfoRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenWritten_then_CREATE_1(@Payload Written written) {
        try {
            if (!written.validate()) return;

            // view 객체 생성
            BookDetailInfo bookDetailInfo = new BookDetailInfo();
            // view 객체에 이벤트의 Value 를 set 함
            bookDetailInfo.setBookId(written.getBookId());
            bookDetailInfo.setTitle(written.getTitle());
            bookDetailInfo.setContent(written.getContent());
            bookDetailInfo.setWriterId(written.getWriterId());
            // view 레파지 토리에 save
            bookDetailInfoRepository.save(bookDetailInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookCoverSelected_then_UPDATE_1(
        @Payload BookCoverSelected bookCoverSelected
    ) {
        try {
            if (!bookCoverSelected.validate()) return;
            // view 객체 조회
            Optional<BookDetailInfo> bookDetailInfoOptional = bookDetailInfoRepository.findById(
                bookCoverSelected.getBookId()
            );

            if (bookDetailInfoOptional.isPresent()) {
                BookDetailInfo bookDetailInfo = bookDetailInfoOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                bookDetailInfo.setCoverUrl(bookCoverSelected.getCoverUrl());
                // view 레파지 토리에 save
                bookDetailInfoRepository.save(bookDetailInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookViewIncreased_then_UPDATE_2(
        @Payload BookViewIncreased bookViewIncreased
    ) {
        try {
            if (!bookViewIncreased.validate()) return;
            // view 객체 조회
            Optional<BookDetailInfo> bookDetailInfoOptional = bookDetailInfoRepository.findById(
                bookViewIncreased.getBookId()
            );

            if (bookDetailInfoOptional.isPresent()) {
                BookDetailInfo bookDetailInfo = bookDetailInfoOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                bookDetailInfo.setViewCount(bookViewIncreased.getViewCount());
                // view 레파지 토리에 save
                bookDetailInfoRepository.save(bookDetailInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRegistered_then_UPDATE_3(@Payload Registered registered) {
        try {
            if (!registered.validate()) return;
            // view 객체 조회
            Optional<BookDetailInfo> bookDetailInfoOptional = bookDetailInfoRepository.findById(
                registered.getUserId()
            );

            if (bookDetailInfoOptional.isPresent()) {
                BookDetailInfo bookDetailInfo = bookDetailInfoOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                bookDetailInfo.setWriterNickname(registered.getNickname());
                // view 레파지 토리에 save
                bookDetailInfoRepository.save(bookDetailInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRegistered_then_UPDATE_4(@Payload Registered registered) {
        try {
            if (!registered.validate()) return;
            // view 객체 조회
            Optional<BookDetailInfo> bookDetailInfoOptional = bookDetailInfoRepository.findById(
                registered.getUserId()
            );

            if (bookDetailInfoOptional.isPresent()) {
                BookDetailInfo bookDetailInfo = bookDetailInfoOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                bookDetailInfo.setWriterNickname(registered.getNickname());
                // view 레파지 토리에 save
                bookDetailInfoRepository.save(bookDetailInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeleted_then_DELETE_1(@Payload Deleted deleted) {
        try {
            if (!deleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            bookDetailInfoRepository.deleteById(deleted.getBookId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}

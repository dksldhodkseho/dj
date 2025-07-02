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
public class BookListViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private BookListRepository bookListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPublishCompleted_then_CREATE_1(
        @Payload PublishCompleted publishCompleted
    ) {
        try {
            if (!publishCompleted.validate()) return;

            // view 객체 생성
            BookList bookList = new BookList();
            // view 객체에 이벤트의 Value 를 set 함
            bookList.setBookId(publishCompleted.getBookId());
            bookList.setTitle(publishCompleted.getTitle());
            bookList.setCoverUrl(publishCompleted.getCoverUrl());
            bookList.setViewCount(0);
            bookList.setWriterId(publishCompleted.getWriterId());
            bookList.setWriterNickname(publishCompleted.getWriterNickname());
            // view 레파지 토리에 save
            bookListRepository.save(bookList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookViewed_then_UPDATE_1(@Payload BookViewed bookViewed) {
        try {
            if (!bookViewed.validate()) return;
            // view 객체 조회
            Optional<BookList> bookListOptional = bookListRepository.findByBookId(
                bookViewed.getBookId()
            );

            if (bookListOptional.isPresent()) {
                BookList bookList = bookListOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                bookList.setViewCount(bookList.getViewCount() + 1);
                // view 레파지 토리에 save
                bookListRepository.save(bookList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRegistered_then_UPDATE_2(@Payload Registered registered) {
        try {
            if (!registered.validate()) return;
            // view 객체 조회

            List<BookList> bookListList = bookListRepository.findByWriterId(
                registered.getUserId()
            );
            for (BookList bookList : bookListList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                bookList.setWriterNickname(registered.getNickname());
                // view 레파지 토리에 save
                bookListRepository.save(bookList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}

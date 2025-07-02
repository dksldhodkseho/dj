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

    @Autowired
    private BookListRepository bookListRepository;

    /**
     * '출간완료됨' 이벤트 수신 시, BookList View 생성 (수정 없음, 아주 좋습니다)
     */
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='PublishCompleted'")
    public void whenPublishCompleted_then_CREATE_1(
        @Payload PublishCompleted publishCompleted
    ) {
        try {
            if (!publishCompleted.validate()) return;
            BookList bookList = new BookList();
            bookList.setBookId(publishCompleted.getBookId());
            bookList.setTitle(publishCompleted.getTitle());
            bookList.setCoverUrl(publishCompleted.getCoverUrl());
            bookList.setViewCount(0);
            bookList.setWriterId(publishCompleted.getWriterId());
            bookList.setWriterNickname(publishCompleted.getWriterNickname());
            bookListRepository.save(bookList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * '도서열람됨' 이벤트 수신 시, viewCount 업데이트 (condition 추가)
     */
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='BookViewed'") // <-- condition 추가
    public void whenBookViewed_then_UPDATE_1(@Payload BookViewed bookViewed) {
        try {
            if (!bookViewed.validate()) return;
            
            Optional<BookList> bookListOptional = bookListRepository.findByBookId(
                bookViewed.getBookId()
            );

            if (bookListOptional.isPresent()) {
                BookList bookList = bookListOptional.get();
                bookList.setViewCount(bookList.getViewCount() + 1);
                bookListRepository.save(bookList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * '사용자 등록됨' 이벤트는 BookList의 writerNickname 업데이트에 적합하지 않으므로 주석 처리 또는 삭제를 권장합니다.
     * PublishCompleted 이벤트에 이미 닉네임이 포함되어 있기 때문입니다.
     * 나중에 '닉네임 변경' 기능이 추가되면, 'NicknameChanged'와 같은 이벤트를 새로 만들어 처리하는 것이 좋습니다.
     */
    /*
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='Registered'") // <-- condition 추가
    public void whenRegistered_then_UPDATE_2(@Payload Registered registered) {
        try {
            if (!registered.validate()) return;
            
            List<BookList> bookListList = bookListRepository.findByWriterId(
                registered.getUserId()
            );
            for (BookList bookList : bookListList) {
                bookList.setWriterNickname(registered.getNickname());
                bookListRepository.save(bookList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}

package miniproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniproject.BookApplication;
import miniproject.domain.PublishCompleted;

@Entity
@Table(name = "Book_table")
@Data
//<<< DDD / Aggregate Root
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private String title;
    private String content;
    private String writerNickname;
    private Long writerId;
    private String coverUrl;
    private String status;

    public static BookRepository repository() {
        BookRepository bookRepository = BookApplication.applicationContext.getBean(
            BookRepository.class
        );
        return bookRepository;
    }

    public void write(WriteCommand writeCommand) {
        // [수정] 파라미터 변수명을 writeCommand로 통일
        if (writeCommand.getTitle() == null || writeCommand.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("도서의 제목은 필수 항목입니다.");
        }
        
        this.setTitle(writeCommand.getTitle());
        this.setContent(writeCommand.getContent());
        this.setWriterId(writeCommand.getWriterId());
        this.setWriterNickname(writeCommand.getWriterNickname());
        this.setStatus("DRAFT");

        Written written = new Written(this);
        written.publishAfterCommit();
    }

    public void delete(DeleteCommand deleteCommand) {
        // 다른 기능 구현 시 이 부분에 로직을 추가하면 됩니다.
        Deleted deleted = new Deleted(this);
        deleted.publishAfterCommit();
    }
    
    // --- [수정] publishRequest 메서드에 로직 추가 ---
    /**
     * '출간 요청'을 처리하는 핵심 비즈니스 로직
     */
    public void publishRequest() { // Command 객체가 필요 없으므로 파라미터 제거
        // 1. 비즈니스 규칙 검증: 이미 요청했거나 출간된 책은 요청 불가
        if ("PUBLISH_REQUESTED".equals(this.status) || "PUBLISHED".equals(this.status)) {
            throw new IllegalStateException("이미 출간 요청되었거나 완료된 도서입니다.");
        }
        
        // 2. 상태 변경: 이 Book 객체의 상태를 "PUBLISH_REQUESTED"로 변경
        this.setStatus("PUBLISH_REQUESTED");

        // 3. 이벤트 발행
        PublishRequested publishRequested = new PublishRequested(this);
        publishRequested.publishAfterCommit();
    }
    // --- 로직 추가 끝 ---

    public void viewBook(ViewBookCommand viewBookCommand) {
        BookViewed bookViewed = new BookViewed(this);
        bookViewed.publishAfterCommit();
    }

    public void selectBookCover(SelectBookCoverCommand selectBookCoverCommand) {
        BookCoverSelected bookCoverSelected = new BookCoverSelected(this);
        bookCoverSelected.publishAfterCommit();
    }

    public void requestCoverGeneration(
        RequestCoverGenerationCommand requestCoverGenerationCommand
    ) {
        CoverGenerationRequested coverGenerationRequested = new CoverGenerationRequested(
            this
        );
        coverGenerationRequested.publishAfterCommit();
    }

    public void update(UpdateCommand updateCommand) {
        Updated updated = new Updated(this);
        updated.publishAfterCommit();
    }

    // --- [수정] publishComplete 메서드 수정 ---
    /**
     * PolicyHandler에 의해 호출되어 최종 출간을 완료 처리하는 메서드
     */
    public void publishComplete() { // static 제거, 파라미터 제거
        // 1. 상태를 'PUBLISHED'(출간 완료)로 변경합니다.
        this.setStatus("PUBLISHED");

        // 2. 'PublishCompleted'(출간 완료됨) 이벤트를 발행하여 View 등에 최종 상태를 알립니다.
        PublishCompleted publishCompleted = new PublishCompleted(this);
        publishCompleted.publishAfterCommit();
    }
    // --- 메서드 수정 끝 ---


    public static void coverCandidatesReady(CoverCreated coverCreated) {
        // 이 부분은 다른 기능 구현 시 수정합니다.
    }
}
//>>> DDD / Aggregate Root

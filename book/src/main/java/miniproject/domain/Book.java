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

    //<<< Clean Arch / Port Method
    public void write(WriteCommand writeCommand) {
        //implement business logic here:
        // [1] 비즈니스 규칙 검증 (Validation)
        // 예: 제목은 비어있을 수 없다.
        if (command.getTitle() == null || command.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("도서의 제목은 필수 항목입니다.");
        }
        // 추가적으로 내용 길이 제한, 작가 ID 유효성 검사 등을 추가할 수 있습니다.


        // [2] Aggregate 상태 변경 (State Change)
        // Command 객체에 담겨온 데이터를 현재 Book Aggregate의 필드에 채워 넣습니다.
        this.setTitle(command.getTitle());
        this.setContent(command.getContent());
        this.setWriterId(command.getWriterId());
        this.setWriterNickname(command.getWriterNickname()); // Command에 닉네임이 있다면 설정
        this.setStatus("DRAFT"); // 초기 상태를 '초고' 또는 '작성중'으로 설정

        Written written = new Written(this);
        written.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void delete(DeleteCommand deleteCommand) {
        //implement business logic here:

        Deleted deleted = new Deleted(this);
        deleted.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void publishRequest(PublishRequestCommand publishRequestCommand) {
        //implement business logic here:

        PublishRequested publishRequested = new PublishRequested(this);
        publishRequested.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void viewBook(ViewBookCommand viewBookCommand) {
        //implement business logic here:

        BookViewed bookViewed = new BookViewed(this);
        bookViewed.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void selectBookCover(SelectBookCoverCommand selectBookCoverCommand) {
        //implement business logic here:

        BookCoverSelected bookCoverSelected = new BookCoverSelected(this);
        bookCoverSelected.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void requestCoverGeneration(
        RequestCoverGenerationCommand requestCoverGenerationCommand
    ) {
        //implement business logic here:

        CoverGenerationRequested coverGenerationRequested = new CoverGenerationRequested(
            this
        );
        coverGenerationRequested.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void update(UpdateCommand updateCommand) {
        //implement business logic here:

        Updated updated = new Updated(this);
        updated.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void publishComplete(PubApproved pubApproved) {
        //implement business logic here:

        /** Example 1:  new item 
        Book book = new Book();
        repository().save(book);

        PublishCompleted publishCompleted = new PublishCompleted(book);
        publishCompleted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(pubApproved.get???()).ifPresent(book->{
            
            book // do something
            repository().save(book);

            PublishCompleted publishCompleted = new PublishCompleted(book);
            publishCompleted.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void coverCandidatesReady(CoverCreated coverCreated) {
        //implement business logic here:

        /** Example 1:  new item 
        Book book = new Book();
        repository().save(book);

        */

        /** Example 2:  finding and process
        
        // if coverCreated.genAiId exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<, Object> openAiMap = mapper.convertValue(coverCreated.getGenAiId(), Map.class);

        repository().findById(coverCreated.get???()).ifPresent(book->{
            
            book // do something
            repository().save(book);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root

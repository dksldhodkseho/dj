package miniproject.infra;

import java.util.Optional;
import javax.transaction.Transactional;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/books") // 클래스 레벨에서 공통 경로를 설정하여 코드를 간결하게 합니다.
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;

    // POST /books/write - 새 도서 작성
    @PostMapping("/write")
    public Book write(@RequestBody WriteCommand writeCommand) throws Exception {
        System.out.println("##### /books/write  called #####");
        Book book = new Book();
        book.write(writeCommand); // Aggregate의 write 메서드 호출
        bookRepository.save(book);
        return book;
    }

    // DELETE /books/{id}/delete - 특정 도서 삭제
    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable(value = "id") Long id) throws Exception {
        System.out.println("##### /books/" + id + "/delete  called #####");
        // FIX: 삭제할 책을 ID로 먼저 조회합니다.
        bookRepository.findById(id).ifPresent(book -> {
            book.delete(); // Aggregate의 delete 메서드 호출 (내부에서 Deleted 이벤트 발행)
            bookRepository.save(book); // CHANGED: 이벤트를 발행하기 위해 save를 호출합니다.
        });
    }

    // POST /books/{id}/publishrequest - 특정 도서 출간 요청
    @PostMapping("/{id}/publishrequest")
    public void publishRequest(@PathVariable(value = "id") Long id) throws Exception {
        System.out.println("##### /books/" + id + "/publishRequest  called #####");
        // FIX: new Book() 대신, ID로 기존 책을 조회합니다.
        bookRepository.findById(id).ifPresent(book -> {
            book.publishRequest(); // Command 객체가 필요 없는 경우, 파라미터 없이 호출
            bookRepository.save(book);
        });
    }

    // PUT /books/{id}/viewbook - 특정 도서 조회(조회수 증가 등)
    @PutMapping("/{id}/viewbook")
    public Book viewBook(@PathVariable(value = "id") Long id) throws Exception {
        System.out.println("##### /books/" + id + "/viewBook  called #####");
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("No Entity Found"));
        book.viewBook(); // Command 객체가 필요 없는 경우, 파라미터 없이 호출
        bookRepository.save(book);
        return book;
    }

    // PUT /books/{id}/selectbookcover - 특정 도서 표지 선택
    @PutMapping("/{id}/selectbookcover")
    public Book selectBookCover(@PathVariable(value = "id") Long id, @RequestBody SelectBookCoverCommand command) throws Exception {
        System.out.println("##### /books/" + id + "/selectBookCover  called #####");
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("No Entity Found"));
        book.selectBookCover(command);
        bookRepository.save(book);
        return book;
    }

    // POST /books/{id}/requestcovergeneration - 특정 도서 표지 생성 요청
    @PostMapping("/{id}/requestcovergeneration")
    public void requestCoverGeneration(@PathVariable(value = "id") Long id, @RequestBody RequestCoverGenerationCommand command) throws Exception {
        System.out.println("##### /books/" + id + "/requestCoverGeneration  called #####");
        // FIX: new Book() 대신, ID로 기존 책을 조회합니다.
        command.setBookId(id); // command 객체에 bookId를 설정해줍니다.
        
        bookRepository.findById(id).ifPresent(book -> {
            book.requestCoverGeneration(command);
            bookRepository.save(book);
        });
    }

    // PUT /books/{id}/update - 특정 도서 정보 수정
    @PutMapping("/{id}/update")
    public Book update(@PathVariable(value = "id") Long id, @RequestBody UpdateCommand command) throws Exception {
        System.out.println("##### /books/" + id + "/update  called #####");
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("No Entity Found"));
        book.update(command);
        bookRepository.save(book);
        return book;
    }
}
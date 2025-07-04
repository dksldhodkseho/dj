package miniproject.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Book write(WriteCommand writeCommand) {
        Book book = new Book();
        book.write(writeCommand);
        bookRepository.save(book);
        return book;
    }

    public void delete(Long id, DeleteCommand command) throws Exception {
        bookRepository.findById(id).ifPresent(book -> {
            book.delete(command);
            bookRepository.save(book);
        });
    }

    public void publishRequest(Long id) throws Exception {
        bookRepository.findById(id).ifPresent(book -> {
            book.publishRequest();
            bookRepository.save(book);
        });
    }

    public Book viewBook(Long id, ViewBookCommand command) throws Exception {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new Exception("No Entity Found"));
        
        // ViewBookCommand에 요청한 사용자의 ID를 담아서 전달해야 합니다.
        command.setUserId(command.getUserId()); // 이 부분은 실제 userId를 받아오는 로직이 필요합니다.
        
        book.viewBook(command);
        bookRepository.save(book);
        return book;
    }
    
    public Book update(Long id, UpdateCommand command) throws Exception {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new Exception("No Entity Found"));

        book.update(command);
        bookRepository.save(book);
        return book;
    }
    
    // ... (향후 다른 서비스 로직 추가 가능) ...
}
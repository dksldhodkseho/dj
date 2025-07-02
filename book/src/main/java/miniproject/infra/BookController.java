package miniproject.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/books")
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(
        value = "/books/write",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Book write(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody WriteCommand writeCommand
    ) throws Exception {
        System.out.println("##### /book/write  called #####");
        Book book = new Book();
        book.write(writeCommand);
        bookRepository.save(book);
        return book;
    }

    @RequestMapping(
        value = "/books/{id}/delete",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public Book delete(
        @PathVariable(value = "id") Long id,
        @RequestBody DeleteCommand deleteCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /book/delete  called #####");
        Optional<Book> optionalBook = bookRepository.findById(id);

        optionalBook.orElseThrow(() -> new Exception("No Entity Found"));
        Book book = optionalBook.get();
        book.delete(deleteCommand);

        bookRepository.delete(book);
        return book;
    }

    @RequestMapping(
        value = "/books/publishrequest",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Book publishRequest(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody PublishRequestCommand publishRequestCommand
    ) throws Exception {
        System.out.println("##### /book/publishRequest  called #####");
        Book book = new Book();
        book.publishRequest(publishRequestCommand);
        bookRepository.save(book);
        return book;
    }

    @RequestMapping(
        value = "/books/{id}/viewbook",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Book viewBook(
        @PathVariable(value = "id") Long id,
        @RequestBody ViewBookCommand viewBookCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /book/viewBook  called #####");
        Optional<Book> optionalBook = bookRepository.findById(id);

        optionalBook.orElseThrow(() -> new Exception("No Entity Found"));
        Book book = optionalBook.get();
        book.viewBook(viewBookCommand);

        bookRepository.save(book);
        return book;
    }

    @RequestMapping(
        value = "/books/{id}/selectbookcover",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Book selectBookCover(
        @PathVariable(value = "id") Long id,
        @RequestBody SelectBookCoverCommand selectBookCoverCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /book/selectBookCover  called #####");
        Optional<Book> optionalBook = bookRepository.findById(id);

        optionalBook.orElseThrow(() -> new Exception("No Entity Found"));
        Book book = optionalBook.get();
        book.selectBookCover(selectBookCoverCommand);

        bookRepository.save(book);
        return book;
    }

    @RequestMapping(
        value = "/books/requestcovergeneration",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Book requestCoverGeneration(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody RequestCoverGenerationCommand requestCoverGenerationCommand
    ) throws Exception {
        System.out.println("##### /book/requestCoverGeneration  called #####");
        Book book = new Book();
        book.requestCoverGeneration(requestCoverGenerationCommand);
        bookRepository.save(book);
        return book;
    }

    @RequestMapping(
        value = "/books/{id}/update",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Book update(
        @PathVariable(value = "id") Long id,
        @RequestBody UpdateCommand updateCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /book/update  called #####");
        Optional<Book> optionalBook = bookRepository.findById(id);

        optionalBook.orElseThrow(() -> new Exception("No Entity Found"));
        Book book = optionalBook.get();
        book.update(updateCommand);

        bookRepository.save(book);
        return book;
    }
}
//>>> Clean Arch / Inbound Adaptor

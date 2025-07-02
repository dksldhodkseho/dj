package miniproject.infra;

import java.util.List;
import java.util.Optional;
import miniproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "bookLists", path = "bookLists")
public interface BookListRepository
    extends PagingAndSortingRepository<BookList, Long> {
    List<BookList> findByWriterId(Long writerId);
    Optional<BookList> findByBookId(Long bookId);
}

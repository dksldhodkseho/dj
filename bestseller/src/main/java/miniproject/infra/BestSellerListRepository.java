package miniproject.infra;

import java.util.List;
import java.util.Optional;
import miniproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "bestSellerLists",
    path = "bestSellerLists"
)
public interface BestSellerListRepository
    extends PagingAndSortingRepository<BestSellerList, Long> {
        Optional<BestSellerList> findByBookId(Long bookId);
    }

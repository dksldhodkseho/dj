package miniproject.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface BookViewCountRepository extends PagingAndSortingRepository<BookViewCount, Long> {
    
    /**
     * 조회수(viewCount)가 높은 순서대로 상위 10개를 조회하는 메서드
     */
    List<BookViewCount> findTop10ByOrderByViewCountDesc();
}
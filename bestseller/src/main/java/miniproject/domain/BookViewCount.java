// In: bestseller/src/main/java/{패키지명}/domain/BookViewCount.java
package miniproject.domain;
import javax.persistence.*;
import lombok.Data;
@Entity
@Table(name="BookViewCount_table")
@Data
public class BookViewCount {
    @Id
    private Long bookId;
    private String title;
    private Long viewCount;
}

// In: bestseller/src/main/java/{패키지명}/domain/BookViewCountRepository.java
package miniproject.domain;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;
public interface BookViewCountRepository extends PagingAndSortingRepository<BookViewCount, Long> {
    // 조회수(viewCount)가 높은 순서대로 상위 10개를 조회하는 메서드
    List<BookViewCount> findTop10ByOrderByViewCountDesc();
}
// In: bestseller/src/main/java/{패키지명}/domain/BookViewed.java
package miniproject.domain;
import lombok.Data;
import miniproject.infra.AbstractEvent;
@Data
public class BookViewed extends AbstractEvent {
    private Long bookId;
    private Integer viewCount;
}
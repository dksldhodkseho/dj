// In: subscription/src/main/java/{패키지명}/domain/BookViewRequested.java
package miniproject.domain;

import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class BookViewRequested extends AbstractEvent {

    private Long bookId;
    private String title;
    private Long userId; // **중요**: 어떤 사용자가 요청했는지 알려주는 ID

    public BookViewRequested(){
        super();
    }
}
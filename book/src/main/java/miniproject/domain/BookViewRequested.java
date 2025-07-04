package miniproject.domain;

import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class BookViewRequested extends AbstractEvent {
    private Long bookId;
    private Long userId;

    public BookViewRequested(Book aggregate) {
        super(aggregate);
    }
    public BookViewRequested() {
        super();
    }
}
package miniproject.domain;

import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class BookCoverUpdated extends AbstractEvent {
    private Long bookId;
    private String coverUrl;

    public BookCoverUpdated(Book aggregate) {
        super(aggregate);
    }
    public BookCoverUpdated() {
        super();
    }
}
package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BookCoverSelected extends AbstractEvent {

    private Long bookId;
    private String coverUrl;

    public BookCoverSelected(Book aggregate) {
        super(aggregate);
    }

    public BookCoverSelected() {
        super();
    }
}
//>>> DDD / Domain Event

package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BookViewed extends AbstractEvent {

    private Long bookId;
    private Long writerId;

    public BookViewed(Book aggregate) {
        super(aggregate);
    }

    public BookViewed() {
        super();
    }
}
//>>> DDD / Domain Event

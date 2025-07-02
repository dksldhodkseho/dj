package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BookAccessDenied extends AbstractEvent {

    private Long userId;
    private String bookId;

    public BookAccessDenied(Subscription aggregate) {
        super(aggregate);
    }

    public BookAccessDenied() {
        super();
    }
}
//>>> DDD / Domain Event

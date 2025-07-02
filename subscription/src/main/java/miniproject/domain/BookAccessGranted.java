package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BookAccessGranted extends AbstractEvent {

    private Long userId;
    private String bookId;

    public BookAccessGranted(Subscription aggregate) {
        super(aggregate);
    }

    public BookAccessGranted() {
        super();
    }
}
//>>> DDD / Domain Event

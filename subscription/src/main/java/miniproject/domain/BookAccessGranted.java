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

    private Long id; // 이 이벤트 자체의 ID
    private Long userId;
    private Long bookId;

    public BookAccessGranted(Subscription aggregate) {
        super(aggregate);
    }

    public BookAccessGranted() {
        super();
    }
}
//>>> DDD / Domain Event

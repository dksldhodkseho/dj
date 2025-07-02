package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BookViewIncreased extends AbstractEvent {

    private Long bookId;
    private Integer viewCount;

    public BookViewIncreased(BestSeller aggregate) {
        super(aggregate);
    }

    public BookViewIncreased() {
        super();
    }
}
//>>> DDD / Domain Event

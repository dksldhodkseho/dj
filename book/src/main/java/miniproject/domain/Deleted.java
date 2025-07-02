package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class Deleted extends AbstractEvent {

    private Long bookId;
    private String title;
    private String content;
    private String coverUrl;

    public Deleted(Book aggregate) {
        super(aggregate);
    }

    public Deleted() {
        super();
    }
}
//>>> DDD / Domain Event

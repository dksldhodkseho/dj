package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PublishRequested extends AbstractEvent {

    private Long bookId;
    private String title;
    private String content;
    private Long writerId;
    private String coverUrl;

    public PublishRequested(Book aggregate) {
        super(aggregate);
    }

    public PublishRequested() {
        super();
    }
}
//>>> DDD / Domain Event

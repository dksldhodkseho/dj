package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PublishCompleted extends AbstractEvent {

    private Long bookId;
    private String title;
    private String content;
    private Long writerId;
    private String coverUrl;
    private String writerNickname;
    private String writerNickname;

    public PublishCompleted(Book aggregate) {
        super(aggregate);
    }

    public PublishCompleted() {
        super();
    }
}
//>>> DDD / Domain Event

package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class Written extends AbstractEvent {

    private Long bookId;
    private String title;
    private String content;
    private Long writerId;
    private String writerNickname;
    private String status;
    private String writerNickname;
    private String status;

    public Written(Book aggregate) {
        super(aggregate);
    }

    public Written() {
        super();
    }
}
//>>> DDD / Domain Event

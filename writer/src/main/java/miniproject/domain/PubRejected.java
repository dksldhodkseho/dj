package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
@Getter
public class PubRejected extends AbstractEvent {

    private Long writerId;
    private Long bookId;
    private String publishStatus;

    public PubRejected(Writer aggregate) {
        super(aggregate);
    }

    public PubRejected() {
        super();
    }
}
//>>> DDD / Domain Event

package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class WriterRejected extends AbstractEvent {

    private Long writerId;
    private String approvalStatus;

    public WriterRejected(Writer aggregate) {
        super(aggregate);
    }

    public WriterRejected() {
        super();
    }
}
//>>> DDD / Domain Event

package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class WriterApproved extends AbstractEvent {

    private Long writerId;
    private String approvalStatus;

    public WriterApproved(Writer aggregate) {
        super(aggregate);
    }

    public WriterApproved() {
        super();
    }
}
//>>> DDD / Domain Event

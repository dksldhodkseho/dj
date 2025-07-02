package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PubApproved extends AbstractEvent {

    private Long writerId;
    private String approvalStatus;
    private String publishStatus;

    public PubApproved(Writer aggregate) {
        super(aggregate);
    }

    public PubApproved() {
        super();
    }
}
//>>> DDD / Domain Event

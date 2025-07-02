package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionCancelRequested extends AbstractEvent {

    private Long userId;

    public SubscriptionCancelRequested(User aggregate) {
        super(aggregate);
    }

    public SubscriptionCancelRequested() {
        super();
    }
}
//>>> DDD / Domain Event

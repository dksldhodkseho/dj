package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionRegistered extends AbstractEvent {

    private Long userId;
    private String subscriptionStatus;
    private Date subscriptionExpiryDate;

    public SubscriptionRegistered(Subscription aggregate) {
        super(aggregate);
    }

    public SubscriptionRegistered() {
        super();
    }
}
//>>> DDD / Domain Event

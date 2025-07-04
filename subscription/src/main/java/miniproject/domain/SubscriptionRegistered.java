package miniproject.domain;

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class SubscriptionRegistered extends AbstractEvent {

    private Long id;
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
package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointChargeRequested extends AbstractEvent {

    private Long userId;
    private String amount;

    public PointChargeRequested(User aggregate) {
        super(aggregate);
    }

    public PointChargeRequested() {
        super();
    }
}
//>>> DDD / Domain Event

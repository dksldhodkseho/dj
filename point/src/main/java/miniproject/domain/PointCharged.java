package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointCharged extends AbstractEvent {

    private Long userId;
    private Integer amount;

    public PointCharged(Point aggregate) {
        super(aggregate);
    }

    public PointCharged() {
        super();
    }
}
//>>> DDD / Domain Event

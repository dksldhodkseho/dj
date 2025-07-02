package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointDeductFailed extends AbstractEvent {

    private Long userId;
    private Integer amount;

    public PointDeductFailed(Point aggregate) {
        super(aggregate);
    }

    public PointDeductFailed() {
        super();
    }
}
//>>> DDD / Domain Event

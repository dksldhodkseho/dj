package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class CoverGenerationRequested extends AbstractEvent {

    private Long bookId;
    private String title;
    private String content;

    public CoverGenerationRequested(Book aggregate) {
        super(aggregate);
    }

    public CoverGenerationRequested() {
        super();
    }
}
//>>> DDD / Domain Event

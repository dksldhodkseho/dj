package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
@Getter
@Setter
public class BestsellerSelected extends AbstractEvent {

    private Long bestsellerId;
    private String bookId;
    private Integer viewCount;
    private String selectedStatus;
    private Date selectedAt;
    private String title;
    private String coverUrl;
    private Long writerId;

    public BestsellerSelected(BestSeller aggregate) {
        super(aggregate);
    }

    public BestsellerSelected() {
        super();
    }

}
//>>> DDD / Domain Event

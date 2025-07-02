package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@Getter
@Setter
public class BookViewIncreased extends AbstractEvent {

    private Long bookId;
    private Integer viewCount;
}

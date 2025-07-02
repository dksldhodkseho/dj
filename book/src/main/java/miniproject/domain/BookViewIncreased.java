package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class BookViewIncreased extends AbstractEvent {

    private Long bookId;
    private Integer viewCount;
}

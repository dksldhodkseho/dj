package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class IncreaseBookViewCommand {

    private Long bookId;
    private Integer viewCount;
}

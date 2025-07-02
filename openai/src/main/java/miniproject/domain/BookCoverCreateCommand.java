package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class BookCoverCreateCommand {

    private Long requestId;
    private Long bookId;
    private String prompt;
}

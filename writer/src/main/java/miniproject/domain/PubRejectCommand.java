package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class PubRejectCommand {

    private Long writerId;
    private Long bookId;
}

package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class DeleteCommand {

    private Long bookId;
    private Long writerId;
}

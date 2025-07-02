package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class CheckSubscriptionCommand {

    private Long userId;
    private String bookId;
}

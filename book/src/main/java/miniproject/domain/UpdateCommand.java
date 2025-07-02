package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class UpdateCommand {

    private Long bookId;
    private Long writerId;
    private String title;
    private String content;
}

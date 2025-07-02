package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RequestCoverGenerationCommand {

    private Long bookId;
    private String title;
    private String content;
    private Long writerId;
}

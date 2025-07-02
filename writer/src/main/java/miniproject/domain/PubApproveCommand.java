package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class PubApproveCommand {

    private Long writerId;
    private Long bookId;
    private String title;
    private String content;
    private String coverUrl;
    private String title;
    private String content;
    private String coverUrl;
}

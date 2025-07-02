package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class WriteCommand {

    private String writerNickname;
    private String title;
    private String content;
    private Long writerId;
}

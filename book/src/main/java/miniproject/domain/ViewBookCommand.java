package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class ViewBookCommand {

    private Long bookId;
    private Long writerId;
}

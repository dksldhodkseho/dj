package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class DeductPointCommand {

    private Long userId;
    private Integer amount;
}

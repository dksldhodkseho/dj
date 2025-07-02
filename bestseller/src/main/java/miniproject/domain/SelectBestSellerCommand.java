package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class SelectBestSellerCommand {

    private Long bestsellerId;
    private Long bookId;
}

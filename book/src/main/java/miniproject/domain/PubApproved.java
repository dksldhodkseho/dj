package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class PubApproved extends AbstractEvent {

    private Long writerId;
    private Long bookId;
    private String approvalStatus;
    private String publishStatus;
}

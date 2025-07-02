package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class PointDeducted extends AbstractEvent {

    private Long userId;
    private Integer amount;
}

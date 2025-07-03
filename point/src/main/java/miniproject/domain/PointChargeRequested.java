package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class PointChargeRequested extends AbstractEvent {
    private Long userId;
    private Integer amount;
}

// In: point/src/main/java/{패키지명}/domain/PointCharged.java
package miniproject.domain;
import lombok.Data;
@Data
public class PointCharged extends AbstractEvent {
    private Long userId;
    private Integer amount;
    public PointCharged(Object aggregate) { super(aggregate); }
    public PointCharged() { super(); }
}

package miniproject.domain;

import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class PointChargeRequested extends AbstractEvent {

    private Long id;
    private Long userId;
    private Integer amount;

    public PointChargeRequested() {
        super();
    }
}
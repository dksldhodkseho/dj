package miniproject.domain;
import lombok.Data;
import miniproject.infra.AbstractEvent;
@Data
public class PointDeductFailed extends AbstractEvent {
    private Long id;
    private Long userId;
    private Integer amount;
    private Long bookId;
}
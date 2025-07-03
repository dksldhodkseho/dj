package miniproject.domain;
import lombok.Data;
import miniproject.infra.AbstractEvent;
@Data
public class PointDeducted extends AbstractEvent {
    private Long id;
    private Long userId;
    private Integer amount;
    private Long bookId;
}
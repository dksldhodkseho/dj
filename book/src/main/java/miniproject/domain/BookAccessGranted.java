package miniproject.domain;
import lombok.Data;
import miniproject.infra.AbstractEvent;
@Data
public class BookAccessGranted extends AbstractEvent {
    private Long id;
    private Long bookId;
    private Long userId;
}
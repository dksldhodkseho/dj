// In: book/src/main/java/{패키지명}/domain/PubRejected.java (신규 생성)
package miniproject.domain;

import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class PubRejected extends AbstractEvent {
    private Long writerId;
    private Long bookId;
    private String publishStatus;
}
// In: point/src/main/java/{패키지명}/domain/PointDeductionRequested.java
package miniproject.domain;

import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class PointDeductionRequested extends AbstractEvent {
    // subscription 서비스에서 보낸 이벤트의 필드와 동일하게 작성합니다.
    private Long id;
    private Long bookId;
    private Long userId;
    private Integer amount;
}
package miniproject.domain;

import lombok.Data;
import miniproject.infra.AbstractEvent; // <-- [수정] 이 import 구문을 추가합니다.

@Data
public class WriterApproved extends AbstractEvent {
    private Long writerId;
}
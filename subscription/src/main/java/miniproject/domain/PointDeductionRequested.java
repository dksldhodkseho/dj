// In: subscription/src/main/java/{패키지명}/domain/PointDeductionRequested.java
package miniproject.domain;

import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class PointDeductionRequested extends AbstractEvent {

    private Long id;
    private Long bookId; // 어떤 책을 보려다 포인트 차감 요청이 발생했는지 추적하기 위함
    private Long userId; // 누구의 포인트를 차감할지
    private Integer amount; // 얼마의 포인트를 차감할지

    public PointDeductionRequested(){
        super();
    }
}
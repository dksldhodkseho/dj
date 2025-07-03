// In: user/src/main/java/miniproject/domain/Registered.java

package miniproject.domain;

import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class Registered extends AbstractEvent {

    private Long userId;
    private String email;
    private String nickname;
    private String role; // [추가] 역할 필드

    public Registered(User aggregate) {
        super(aggregate);
        // User Aggregate의 role 값을 이벤트에 복사합니다.
        if (aggregate != null) {
            this.setRole(aggregate.getRole());
        }
    }
    public Registered() {
        super();
    }
}
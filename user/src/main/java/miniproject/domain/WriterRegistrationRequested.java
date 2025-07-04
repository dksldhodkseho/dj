// In: user/src/main/java/{패키지명}/domain/WriterRegistrationRequested.java
package miniproject.domain;

import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class WriterRegistrationRequested extends AbstractEvent {
    // writer 서비스에서 보낸 이벤트의 필드와 동일하게 작성합니다.
    private Long id; // writer 서비스의 Writer 엔티티 ID
    private String email;
    private String nickname;
    private String password;
}
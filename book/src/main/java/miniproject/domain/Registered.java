package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@Getter
@Setter
public class Registered extends AbstractEvent {

    private Long userId;
    private String email;
    private String nickname;
}

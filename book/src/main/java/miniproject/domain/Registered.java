package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import miniproject.infra.AbstractEvent;

@Data
public class Registered extends AbstractEvent {

    private Long userId;
    private String email;
    private String nickname;
}

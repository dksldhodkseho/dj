package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class SubscriptionRequested extends AbstractEvent {

    private Long userId;
    private String email;
    private String nickname;
    private Boolean subscribed;
    private Boolean writerRequested;
}

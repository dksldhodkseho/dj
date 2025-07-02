package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class SubscriptionCancelRequested extends AbstractEvent {

    private Long userId;
}

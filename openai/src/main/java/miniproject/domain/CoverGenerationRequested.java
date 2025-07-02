package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class CoverGenerationRequested extends AbstractEvent {

    private Long bookId;
    private String title;
    private String content;
}

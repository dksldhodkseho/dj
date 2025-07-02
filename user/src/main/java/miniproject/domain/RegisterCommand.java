package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RegisterCommand {

    private Long userId;
    private String email;
    private String nickname;
}

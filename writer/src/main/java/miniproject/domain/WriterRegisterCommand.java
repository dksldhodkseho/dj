package miniproject.domain;

import lombok.Data;

@Data
public class WriterRegisterCommand {
    private String email;
    private String nickname;
    private String password;
}
package miniproject.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "UserInfoList_table")
@Data
public class UserInfoList {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;

    private String email;
    private String nickname;
}

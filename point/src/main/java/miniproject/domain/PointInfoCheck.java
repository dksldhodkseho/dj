package miniproject.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;

//<<< EDA / CQRS
@Entity
@Table(name = "PointInfoCheck_table")
@Data
@Getter
@Setter
public class PointInfoCheck {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;

    private Integer amount;
}

package miniproject.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "BestSellerList_table")
@Data
public class BestSellerList {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long bookId;

    private String title;
    private String coverUrl;
    private Integer viewCount;
    private Long writerId;
    private String selectedStatus;
    private Date selectedAt;
}

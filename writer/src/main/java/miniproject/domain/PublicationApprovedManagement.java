package miniproject.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "PublicationApprovedManagement_table")
@Data
public class PublicationApprovedManagement {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long bookId;

    private String title;
    private String content;
    private String coverUrl;
    private Long writerId;
    private String publishStatus;
}

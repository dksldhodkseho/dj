package miniproject.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "BookDetailInfo_table")
@Data
public class BookDetailInfo {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long bookId;

    private String title;
    private String content;
    private Integer viewCount;
    private Long writerId;
    private String writerNickname;
    private String coverUrl;
    
}

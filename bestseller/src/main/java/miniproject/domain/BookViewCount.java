package miniproject.domain;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name="BookViewCount_table")
@Data
public class BookViewCount {

    @Id
    private Long bookId;

    private String title;
    
    private Long viewCount;
}
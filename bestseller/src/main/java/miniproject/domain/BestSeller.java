package miniproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniproject.BestsellerApplication;

@Entity
@Table(name = "BestSeller_table")
@Data
//<<< DDD / Aggregate Root
public class BestSeller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bestsellerId;

    private String bookId;

    private Integer viewCount;

    private String selectedStatus;

    private Date selectedAt;

    public static BestSellerRepository repository() {
        BestSellerRepository bestSellerRepository = BestsellerApplication.applicationContext.getBean(
            BestSellerRepository.class
        );
        return bestSellerRepository;
    }

    //<<< Clean Arch / Port Method
    public void increaseBookView(
        IncreaseBookViewCommand increaseBookViewCommand
    ) {
        //implement business logic here:

        BookViewIncreased bookViewIncreased = new BookViewIncreased(this);
        bookViewIncreased.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void selectBestSeller(
        SelectBestSellerCommand selectBestSellerCommand
    ) {
        //implement business logic here:

        BestsellerSelected bestsellerSelected = new BestsellerSelected(this);
        bestsellerSelected.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void viewCount(BookAccessGranted bookAccessGranted) {
        //implement business logic here:

        /** Example 1:  new item 
        BestSeller bestSeller = new BestSeller();
        repository().save(bestSeller);

        */

        /** Example 2:  finding and process
        

        repository().findById(bookAccessGranted.get???()).ifPresent(bestSeller->{
            
            bestSeller // do something
            repository().save(bestSeller);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void viewCount(PointDeducted pointDeducted) {
        //implement business logic here:

        /** Example 1:  new item 
        BestSeller bestSeller = new BestSeller();
        repository().save(bestSeller);

        */

        /** Example 2:  finding and process
        

        repository().findById(pointDeducted.get???()).ifPresent(bestSeller->{
            
            bestSeller // do something
            repository().save(bestSeller);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root

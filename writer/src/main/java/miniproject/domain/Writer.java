package miniproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniproject.WriterApplication;

@Entity
@Table(name = "Writer_table")
@Data
//<<< DDD / Aggregate Root
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long writerId;

    private String approvalStatus;

    public static WriterRepository repository() {
        WriterRepository writerRepository = WriterApplication.applicationContext.getBean(
            WriterRepository.class
        );
        return writerRepository;
    }

    //<<< Clean Arch / Port Method
    public void writerApprove(WriterApproveCommand writerApproveCommand) {
        //implement business logic here:

        WriterApproved writerApproved = new WriterApproved(this);
        writerApproved.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void writerReject(WriterRejectCommand writerRejectCommand) {
        //implement business logic here:

        WriterRejected writerRejected = new WriterRejected(this);
        writerRejected.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void pubApprove(PubApproveCommand pubApproveCommand) {
        //implement business logic here:

        PubApproved pubApproved = new PubApproved(this);
        pubApproved.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void pubReject(PubRejectCommand pubRejectCommand) {
        //implement business logic here:

        PubRejected pubRejected = new PubRejected(this);
        pubRejected.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void writerRequest(WriterRequest writerRequest) {
        //implement business logic here:

        /** Example 1:  new item 
        Writer writer = new Writer();
        repository().save(writer);

        */

        /** Example 2:  finding and process
        

        repository().findById(writerRequest.get???()).ifPresent(writer->{
            
            writer // do something
            repository().save(writer);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void publishRequest(PublishRequested publishRequested) {
        //implement business logic here:

        /** Example 1:  new item 
        Writer writer = new Writer();
        repository().save(writer);

        */

        /** Example 2:  finding and process
        

        repository().findById(publishRequested.get???()).ifPresent(writer->{
            
            writer // do something
            repository().save(writer);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root

package miniproject.domain;

import miniproject.OpenaiApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;


@Entity
@Table(name="OpenAi_table")
@Data

//<<< DDD / Aggregate Root
public class OpenAi  {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
private Long requestId;    
    
    
private Long bookId;    
    
    
private String prompt;    
    
    
private String coverUrl;


    public static OpenAiRepository repository(){
        OpenAiRepository openAiRepository = OpenaiApplication.applicationContext.getBean(OpenAiRepository.class);
        return openAiRepository;
    }



//<<< Clean Arch / Port Method
    public void bookCoverCreate(BookCoverCreateCommand bookCoverCreateCommand){
        
        //implement business logic here:
        

        miniproject.external.OpenAiQuery openAiQuery = new miniproject.external.OpenAiQuery();
        // openAiQuery.set??()        
          = OpenAiApplication.applicationContext
            .getBean(miniproject.external.Service.class)
            .openAi(openAiQuery);

        CoverCreated coverCreated = new CoverCreated(this);
        coverCreated.publishAfterCommit();
    }
//>>> Clean Arch / Port Method

//<<< Clean Arch / Port Method
    public static void coverGenerationRequested(CoverGenerationRequested coverGenerationRequested){
        
        //implement business logic here:
        
        /** Example 1:  new item 
        OpenAi openAi = new OpenAi();
        repository().save(openAi);

        */

        /** Example 2:  finding and process
        

        repository().findById(coverGenerationRequested.get???()).ifPresent(openAi->{
            
            openAi // do something
            repository().save(openAi);


         });
        */

        
    }
//>>> Clean Arch / Port Method


}
//>>> DDD / Aggregate Root

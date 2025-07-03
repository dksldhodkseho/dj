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
    
    // [1] 비즈니스 규칙 검증: 이미 승인된 작가는 아닌지 확인합니다.
    if ("APPROVED".equals(this.getApprovalStatus())) {
        // 이미 처리된 요청이면 여기서 로직을 중단하거나 예외를 발생시킬 수 있습니다.
        System.out.println("이미 승인된 작가입니다. writerId: " + this.getWriterId());
        return; // 중복 실행 방지
    }

    // [2] 상태 변경: 이 Writer 객체의 승인 상태를 "APPROVED"로 변경합니다.
    this.setApprovalStatus("APPROVED");

    // [3] 이벤트 발행: "작가가 승인되었다"는 사실을 다른 서비스에 알리기 위해 이벤트를 발행합니다.
    // 보내주신 코드 그대로 아주 잘 작성되어 있습니다.
    WriterApproved writerApproved = new WriterApproved(this);
    writerApproved.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void writerReject() {
        // 비즈니스 규칙: 이미 처리된 건은 아닌지 확인
        if (!"PENDING".equals(this.getApprovalStatus())) {
            throw new IllegalStateException("이미 처리된 신청 건입니다.");
        }

        // 상태 변경: 승인 상태를 "REJECTED"로 변경
        this.setApprovalStatus("REJECTED");

        // 이벤트 발행: 'WriterRejected' 이벤트를 발행
        WriterRejected writerRejected = new WriterRejected(this);
        writerRejected.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void pubApprove(PubApproveCommand pubApproveCommand) {
        //implement business logic here:
        this.setPublishStatus("APPROVED");

        PubApproved pubApproved = new PubApproved(this);

        pubApproved.setBookId(command.getBookId());
        pubApproved.setTitle(command.getTitle());
        pubApproved.setContent(command.getContent());
        pubApproved.setCoverUrl(command.getCoverUrl());

        pubApproved.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    /**
     * '출간 거절(PubReject)' 커맨드를 처리하는 비즈니스 로직
     */
    public void pubReject(PubRejectCommand command) {
        // [1] 상태 변경 (시나리오에 따라 필요시 추가)
        this.setPublishStatus("REJECTED");

        // [2] 'PubRejected'(출간 거절됨) 이벤트를 발행합니다.
        //    어떤 책이 거절되었는지 알려주기 위해 bookId를 이벤트에 담아줍니다.
        PubRejected pubRejected = new PubRejected(this);
        pubRejected.setBookId(command.getBookId());
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

    public void register(WriterRegisterCommand command) {
    // 이 시점에서는 아직 userId가 없으므로,
    // 받은 정보(email, nickname 등)를 그대로 담아 이벤트를 발행하는 것이 주 목적입니다.
    
    WriterRegistrationRequested event = new WriterRegistrationRequested(this);
    event.setEmail(command.getEmail());
    event.setNickname(command.getNickname());
    event.setPassword(command.getPassword()); // 비밀번호는 이벤트에 담아 한번만 전달하고, DB에는 저장하지 않습니다.
    event.publishAfterCommit();
}
}
//>>> DDD / Aggregate Root

package miniproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniproject.PointApplication;

@Entity
@Table(name = "Point_table")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private Integer amount;

    public static PointRepository repository() {
        PointRepository pointRepository = PointApplication.applicationContext.getBean(
            PointRepository.class
        );
        return pointRepository;
    }

    @PostPersist
    public void onPostPersist() {
        // PointCharged 또는 PointDeducted 이벤트는 각 메서드에서 개별적으로 발행됩니다.
    }

    //<<< Clean Arch / Port Method
    /**
     * 포인트 차감 비즈니스 로직
     * @param command 포인트 차감에 필요한 정보
     */
     public void deductPoint(DeductPointCommand command) {
        if (this.getAmount() < command.getAmount()) {
            PointDeductFailed pointDeductFailed = new PointDeductFailed(this);
            pointDeductFailed.setBookId(command.getBookId());
            pointDeductFailed.setAmount(command.getAmount());
            pointDeductFailed.publishAfterCommit();
        } else {
            this.setAmount(this.getAmount() - command.getAmount());
            PointDeducted pointDeducted = new PointDeducted(this);
            pointDeducted.setBookId(command.getBookId());
            pointDeducted.setAmount(command.getAmount());
            pointDeducted.publishAfterCommit();
        }
    }
    

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    /**
 * 포인트를 충전하는 비즈니스 로직
 * @param chargePointCommand 충전할 포인트 양(amount)을 포함한 커맨드 객체
 */
    public void chargePoint(ChargePointCommand command) {
        if (this.getAmount() == null) {
            this.setAmount(0);
        }
        this.setAmount(this.getAmount() + command.getAmount());
        
        PointCharged pointCharged = new PointCharged(this);
        pointCharged.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void checkPoint(BookAccessDenied bookAccessDenied) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        */

        /** Example 2:  finding and process
        

        repository().findById(bookAccessDenied.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);


         });
        */

    }

    
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root

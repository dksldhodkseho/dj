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

    //<<< Clean Arch / Port Method
    /**
     * 포인트 차감 비즈니스 로직
     * @param command 포인트 차감에 필요한 정보
     */
    public void deductPoint(DeductPointCommand deductPointCommand) {
        // [1] 비즈니스 규칙: 보유 포인트가 차감할 포인트보다 적은지 확인
        if (this.getAmount() < command.getAmount()) {
            
            // [2-1] 실패 경로: PointDeductFailed (포인트 차감 실패) 이벤트 발행
            PointDeductFailed pointDeductFailed = new PointDeductFailed(this);
            pointDeductFailed.setBookId(command.getBookId()); // bookId와 amount를 실패 이벤트에 담아 전달
            pointDeductFailed.setAmount(command.getAmount());
            pointDeductFailed.publishAfterCommit();

        } else {
            // [2-2] 성공 경로: 포인트 차감 및 PointDeducted (포인트 차감 성공) 이벤트 발행
            this.setAmount(this.getAmount() - command.getAmount());

            PointDeducted pointDeducted = new PointDeducted(this);
            pointDeducted.setBookId(command.getBookId());
            pointDeducted.setAmount(command.getAmount());
            pointDeducted.publishAfterCommit();
        }
    }
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void chargePoint(ChargePointCommand chargePointCommand) {
        //implement business logic here:

        PointCharged pointCharged = new PointCharged(this);
        pointCharged.publishAfterCommit();
        PointChargeFailed pointChargeFailed = new PointChargeFailed(this);
        pointChargeFailed.publishAfterCommit();
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
    //<<< Clean Arch / Port Method
    public static void chargePoint(PointChargeRequested pointChargeRequested) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        */

        /** Example 2:  finding and process
        

        repository().findById(pointChargeRequested.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root

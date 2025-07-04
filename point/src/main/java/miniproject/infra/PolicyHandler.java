package miniproject.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // [수정 안 함] 이 리스너는 다른 기능이므로 그대로 둡니다.
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookAccessDenied'"
    )
    public void wheneverBookAccessDenied_CheckPoint(
        @Payload BookAccessDenied bookAccessDenied
    ) {
        BookAccessDenied event = bookAccessDenied;
        System.out.println(
            "\n\n##### listener CheckPoint : " + bookAccessDenied + "\n\n"
        );
        // Sample Logic //
        // Point.checkPoint(event);
    }

    /**
     * [수정된 메서드]
     * user 서비스에서 포인트 충전 요청 이벤트 발생 시, 해당 사용자의 포인트를 증가시킵니다.
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointChargeRequested'"
    )
    public void wheneverPointChargeRequested_ChargePoint(
        @Payload PointChargeRequested pointChargeRequested
    ) {
        try {
            if (!pointChargeRequested.validate()) return;
            System.out.println(
                "\n\n##### listener ChargePoint : " +
                pointChargeRequested.toJson() +
                "\n\n"
            );

            // 1. userId로 포인트 정보를 찾거나, 없으면 amount가 0인 새 Point 객체를 생성합니다.
            // 'orElseGet'은 Optional 객체가 비어있을 경우에만 내부 로직을 실행하여 효율적입니다.
            Point point = pointRepository
                .findByUserId(pointChargeRequested.getUserId())
                .orElseGet(() -> {
                    Point newPoint = new Point();
                    newPoint.setUserId(pointChargeRequested.getUserId());
                    newPoint.setAmount(0); // 최초 생성 시 포인트는 0
                    return newPoint;
                });

            // 2. 이벤트 정보를 Command 객체로 변환합니다.
            ChargePointCommand command = new ChargePointCommand();
            command.setAmount(pointChargeRequested.getAmount());

            // 3. Aggregate의 chargePoint 메서드를 호출하여 포인트를 충전합니다.
            point.chargePoint(command);

            // 4. 변경된 포인트 정보를 DB에 저장합니다.
            pointRepository.save(point);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * [수정 안 함] 이 리스너는 올바르게 구현되어 있으므로 그대로 둡니다.
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointDeductionRequested'"
    )
    public void wheneverPointDeductionRequested_DeductPoint(
        @Payload PointDeductionRequested pointDeductionRequested
    ) {
        try {
            if (!pointDeductionRequested.validate()) return;
            
            // 이벤트에서 받은 정보를 Command 객체로 변환
            DeductPointCommand command = new DeductPointCommand();
            command.setUserId(pointDeductionRequested.getUserId());
            command.setBookId(pointDeductionRequested.getBookId());
            command.setAmount(pointDeductionRequested.getAmount());

            // userId로 포인트 정보를 찾아 포인트 차감 로직(deductPoint)을 실행
            pointRepository
                .findByUserId(command.getUserId())
                .ifPresent(point -> {
                    point.deductPoint(command);
                    pointRepository.save(point);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//>>> Clean Arch / Inbound Adaptor

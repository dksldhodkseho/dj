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
        Point.checkPoint(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointChargeRequested'"
    )
    public void wheneverPointChargeRequested_ChargePoint(
        @Payload PointChargeRequested pointChargeRequested
    ) {
        PointChargeRequested event = pointChargeRequested;
        System.out.println(
            "\n\n##### listener ChargePoint : " + pointChargeRequested + "\n\n"
        );

        // Sample Logic //
        Point.chargePoint(event);
    }

     /**
     * [이벤트 수신] subscription 서비스에서 PointDeductionRequested 이벤트 발생 시
     * 포인트 차감 로직을 실행합니다.
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
            System.out.println(
                "\n\n##### listener DeductPoint : " +
                pointDeductionRequested.toJson() +
                "\n\n"
            );

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

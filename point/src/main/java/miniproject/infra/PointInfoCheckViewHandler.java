package miniproject.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PointInfoCheckViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private PointInfoCheckRepository pointInfoCheckRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointCharged_then_CREATE_1(
        @Payload PointCharged pointCharged
    ) {
        try {
            if (!pointCharged.validate()) return;

            // view 객체 생성
            PointInfoCheck pointInfoCheck = new PointInfoCheck();
            // view 객체에 이벤트의 Value 를 set 함
            pointInfoCheck.setUserId(pointCharged.getUserId());
            pointInfoCheck.setAmount(pointCharged.getAmount());
            pointInfoCheck.setAmount(pointCharged.getAmount());
            // view 레파지 토리에 save
            pointInfoCheckRepository.save(pointInfoCheck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointDeducted_then_UPDATE_1(
        @Payload PointDeducted pointDeducted
    ) {
        try {
            if (!pointDeducted.validate()) return;
            // view 객체 조회
            List<PointInfoCheck> pointInfoCheckOptional = pointInfoCheckRepository.findByUserId(
                pointDeducted.getUserId()
            );

            if (!pointInfoCheckOptional.isEmpty()) {
                PointInfoCheck pointInfoCheck = pointInfoCheckOptional.get(0);
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                pointInfoCheck.setAmount(
                    pointInfoCheck.getAmount() - pointDeducted.getAmount()
                );
                // view 레파지 토리에 save
                pointInfoCheckRepository.save(pointInfoCheck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointCharged_then_UPDATE_2(
        @Payload PointCharged pointCharged
    ) {
        try {
            if (!pointCharged.validate()) return;
            // view 객체 조회
            List<PointInfoCheck> pointInfoCheckOptional = pointInfoCheckRepository.findByUserId(
                pointCharged.getUserId()
            );

            if (!pointInfoCheckOptional.isEmpty()) {
                PointInfoCheck pointInfoCheck = pointInfoCheckOptional.get(0);
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                pointInfoCheck.setAmount(
                    pointInfoCheck.getAmount() + pointCharged.getAmount()
                );
                // view 레파지 토리에 save
                pointInfoCheckRepository.save(pointInfoCheck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointCharged_then_UPDATE_3(
        @Payload PointCharged pointCharged
    ) {
        try {
            if (!pointCharged.validate()) return;
            // view 객체 조회
            List<PointInfoCheck> pointInfoCheckOptional = pointInfoCheckRepository.findByUserId(
                pointCharged.getUserId()
            );

            if (!pointInfoCheckOptional.isEmpty()) {
                PointInfoCheck pointInfoCheck = pointInfoCheckOptional.get(0);
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                pointInfoCheck.setAmount(
                    pointInfoCheck.getAmount() + pointCharged.getAmount()
                );
                // view 레파지 토리에 save
                pointInfoCheckRepository.save(pointInfoCheck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}

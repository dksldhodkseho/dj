package miniproject.infra;

import java.io.IOException;
import java.util.List;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import miniproject.domain.SubscriptionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
public class SubscriptionStatusCheckViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private SubscriptionStatusCheckRepository subscriptionStatusCheckRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionRegistered_then_CREATE_1(
        @Payload SubscriptionRegistered subscriptionRegistered
    ) {
        try {
            if (!subscriptionRegistered.validate()) return;

            // view 객체 생성
            SubscriptionStatusCheck subscriptionStatusCheck = new SubscriptionStatusCheck();
            // view 객체에 이벤트의 Value 를 set 함
            subscriptionStatusCheck.setUserId(
                subscriptionRegistered.getUserId()
            );
            subscriptionStatusCheck.setSubscriptionStatus(SubscriptionStatus.ACTIVE.name()); // 🔧 수정
            subscriptionStatusCheck.setSubscriptionExpireDate(
                String.valueOf(
                    subscriptionRegistered.getSubscriptionExpiryDate()
                )
            );
            // view 레파지 토리에 save
            subscriptionStatusCheckRepository.save(subscriptionStatusCheck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionCanceled_then_UPDATE_1(
        @Payload SubscriptionCanceled subscriptionCanceled
    ) {
        try {
            if (!subscriptionCanceled.validate()) return;
            // view 객체 조회
            List<SubscriptionStatusCheck> subscriptionStatusChecks =
                subscriptionStatusCheckRepository.findByUserId(
                    subscriptionCanceled.getUserId()
                ); // 🔧 수정

            if (!subscriptionStatusChecks.isEmpty()) { // 🔧 수정
                SubscriptionStatusCheck subscriptionStatusCheck = subscriptionStatusChecks.get(0); // 🔧 수정
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                subscriptionStatusCheck.setSubscriptionStatus(SubscriptionStatus.CANCELLED.name()); // 🔧 수정
                // view 레파지 토리에 save
                subscriptionStatusCheckRepository.save(subscriptionStatusCheck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}

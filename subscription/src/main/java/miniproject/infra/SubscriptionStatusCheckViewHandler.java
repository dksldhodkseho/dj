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
            subscriptionStatusCheck.setSubscriptionStatus(ACTIVE);
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
            Optional<SubscriptionStatusCheck> subscriptionStatusCheckOptional = subscriptionStatusCheckRepository.findByUserId(
                subscriptionCanceled.getUserId()
            );

            if (subscriptionStatusCheckOptional.isPresent()) {
                SubscriptionStatusCheck subscriptionStatusCheck = subscriptionStatusCheckOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                subscriptionStatusCheck.setSubscriptionStatus(CANCELLED);
                // view 레파지 토리에 save
                subscriptionStatusCheckRepository.save(subscriptionStatusCheck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}

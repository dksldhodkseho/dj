package miniproject.infra;

import java.util.Optional;
import javax.transaction.Transactional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.sql.Date; // java.sql.Date를 import 합니다.
import java.time.LocalDate;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    /**
     * [이벤트 수신] user 서비스에서 구독 요청 이벤트가 발생했을 때,
     * 실제 구독 정보를 생성합니다.
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionRequested'"
    )
    public void wheneverSubscriptionRequested_CreateSubscription(
        @Payload SubscriptionRequested subscriptionRequested
    ) {
        try {
            if (!subscriptionRequested.validate()) return;
            
            Subscription subscription = new Subscription();
            subscription.setUserId(subscriptionRequested.getUserId());
            subscription.setSubscriptionStatus("ACTIVE");

            // --- [수정] LocalDate를 java.sql.Date로 변환하여 저장 ---
            LocalDate expiryLocalDate = LocalDate.now().plusDays(30);
            subscription.setSubscriptionExpiryDate(Date.valueOf(expiryLocalDate));
            // ----------------------------------------------------
            
            subscriptionRepository.save(subscription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * [이벤트 수신] book 서비스에서 도서 열람 요청 이벤트가 발생했을 때,
     * 사용자의 구독 상태를 확인하고 후속 이벤트를 발행합니다.
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookViewRequested'"
    )
    public void wheneverBookViewRequested_CheckSubscription(
        @Payload BookViewRequested bookViewRequested
    ) {
        try {
            if (!bookViewRequested.validate()) return;
            
            boolean isSubscribed = subscriptionRepository
                .findByUserIdAndSubscriptionStatus(
                    bookViewRequested.getUserId(),
                    "ACTIVE"
                )
                .isPresent();

            if (isSubscribed) {
                BookAccessGranted bookAccessGranted = new BookAccessGranted();
                bookAccessGranted.setBookId(bookViewRequested.getBookId());
                bookAccessGranted.setUserId(bookViewRequested.getUserId());
                bookAccessGranted.publish();
            } else {
                PointDeductionRequested pointDeductionRequested = new PointDeductionRequested();
                pointDeductionRequested.setBookId(bookViewRequested.getBookId());
                
                // --- [수정] Long 타입을 Long 타입 필드에 그대로 전달 ---
                pointDeductionRequested.setUserId(bookViewRequested.getUserId());
                // ----------------------------------------------
                
                pointDeductionRequested.setAmount(100);
                pointDeductionRequested.publish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * [이벤트 수신] user 서비스에서 구독 취소 요청 이벤트 발생 시
     * 해당 사용자의 구독 상태를 'CANCELED'로 변경합니다.
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionCancelRequested'"
    )
    public void wheneverSubscriptionCancelRequested_CancelSubscription(
        @Payload SubscriptionCancelRequested subscriptionCancelRequested
    ) {
        try {
            if (!subscriptionCancelRequested.validate()) return;
            
            subscriptionRepository
                .findByUserIdAndSubscriptionStatus(
                    subscriptionCancelRequested.getUserId(),
                    "ACTIVE"
                )
                .ifPresent(subscription -> {
                    subscription.cancel();
                    subscriptionRepository.save(subscription);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
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
네, 보내주신 subscription 서비스의 PolicyHandler.java 코드를 확인했습니다. 이전 단계의 코드와 새로 구현할 코드가 함께 있어서 약간 혼란스러워 보이는데, 제가 깔끔하게 정리해 드리겠습니다.

수정 사항 설명
핵심은 기존에 있던 wheneverBookViewed_CheckSubscription 메서드를 우리가 새로 구현하려는 로직으로 완전히 교체하는 것입니다. 이전 메서드는 BookViewed라는 다른 이벤트를 수신하고 있었고, 내부 로직도 샘플 코드였기 때문입니다.

우리의 목표는 book 서비스에서 온 BookViewRequested 이벤트를 받아서 구독 상태를 확인하는 것이므로, 이 이벤트에 반응하는 리스너를 정확하게 구현해야 합니다.

요청하신 대로, 다른 부분은 그대로 두고 '구독 상태 확인 및 분기 처리' 로직만 정확하게 수정한 전체 코드를 드리겠습니다.

수정된 PolicyHandler.java 전체 코드
아래 코드를 subscription 서비스의 PolicyHandler.java 파일에 그대로 붙여넣으시면 됩니다.

Java

package miniproject.infra;

import java.util.Optional; // Optional 클래스를 import 합니다.
import javax.transaction.Transactional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    /**
     * [수정 없음] user 서비스에서 구독 요청(SubscriptionRequested) 이벤트가 발생했을 때,
     * 실제 구독 정보를 생성합니다. (이전 단계에서 완성)
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionRequested'"
    )
    public void wheneverSubscriptionRequested_Subscribe(
        @Payload SubscriptionRequested subscriptionRequested
    ) {
        try {
            if (!subscriptionRequested.validate()) return;
            System.out.println(
                "\n\n##### listener Subscribe : " + subscriptionRequested.toJson() + "\n\n"
            );
            Subscription subscription = new Subscription();
            subscription.setUserId(subscriptionRequested.getUserId());
            subscription.setSubscriptionStatus("ACTIVE");
            subscription.setSubscriptionExpiryDate(java.time.LocalDate.now().plusDays(30));
            subscriptionRepository.save(subscription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * [수정된 메서드]
     * book 서비스에서 BookViewRequested 이벤트가 발생했을 때,
     * 사용자의 구독 상태를 확인하고, 그에 맞는 후속 이벤트를 발행합니다.
     * (기존 wheneverBookViewed_CheckSubscription 메서드를 이 로직으로 대체합니다)
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
    
            System.out.println(
                "\n\n##### listener CheckSubscription for BookViewRequested : " +
                bookViewRequested.toJson() +
                "\n\n"
            );
    
            // 1. 자신의 DB에서 사용자의 유효한("ACTIVE") 구독 정보를 조회합니다.
            boolean isSubscribed = subscriptionRepository
                .findByUserIdAndSubscriptionStatus(
                    bookViewRequested.getUserId(),
                    "ACTIVE"
                )
                .isPresent();
    
            if (isSubscribed) {
                // 2-1. [구독자일 경우] "열람 허가" 이벤트를 발행합니다.
                BookAccessGranted bookAccessGranted = new BookAccessGranted();
                bookAccessGranted.setBookId(bookViewRequested.getBookId());
                bookAccessGranted.setUserId(bookViewRequested.getUserId());
                bookAccessGranted.publish();
            } else {
                // 2-2. [구독자가 아닐 경우] "포인트 차감 요청" 이벤트를 발행합니다.
                PointDeductionRequested pointDeductionRequested = new PointDeductionRequested();
                pointDeductionRequested.setBookId(bookViewRequested.getBookId());
                pointDeductionRequested.setUserId(bookViewRequested.getUserId());
                pointDeductionRequested.setAmount(100); // 도서 열람에 필요한 포인트: 100
                pointDeductionRequested.publish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * [수정 안 함] 이 리스너는 다른 기능이므로 그대로 둡니다.
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionCancelRequested'"
    )
    public void wheneverSubscriptionCancelRequested_SubscriptionCancel(
        @Payload SubscriptionCancelRequested subscriptionCancelRequested
    ) {
        System.out.println(
            "\n\n##### listener SubscriptionCancel : " +
            subscriptionCancelRequested +
            "\n\n"
        );
        // Sample Logic
    }
}
//>>> Clean Arch / Inbound Adaptor

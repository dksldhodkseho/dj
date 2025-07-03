package miniproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniproject.SubscriptionApplication;

@Entity
@Table(name = "Subscription_table")
@Data
//<<< DDD / Aggregate Root
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String subscriptionStatus;
    private Date subscriptionExpiryDate;

    public static SubscriptionRepository repository() {
        SubscriptionRepository subscriptionRepository = SubscriptionApplication.applicationContext.getBean(
            SubscriptionRepository.class
        );
        return subscriptionRepository;
    }

    //<<< Clean Arch / Port Method
    public void checkSubscription(
        CheckSubscriptionCommand checkSubscriptionCommand
    ) {
        //implement business logic here:

        BookAccessGranted bookAccessGranted = new BookAccessGranted(this);
        bookAccessGranted.publishAfterCommit();
        BookAccessDenied bookAccessDenied = new BookAccessDenied(this);
        bookAccessDenied.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void subscriptionRegister(
        SubscriptionRegisterCommand subscriptionRegisterCommand
    ) {
        //implement business logic here:

        SubscriptionRegistered subscriptionRegistered = new SubscriptionRegistered(
            this
        );
        subscriptionRegistered.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void subscriptionCancel(
        SubscriptionCancelCommand subscriptionCancelCommand
    ) {
        //implement business logic here:

        SubscriptionCanceled subscriptionCanceled = new SubscriptionCanceled(
            this
        );
        subscriptionCanceled.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void subscribe(SubscriptionRequested subscriptionRequested) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscription subscription = new Subscription();
        repository().save(subscription);

        */

        /** Example 2:  finding and process
        

        repository().findById(subscriptionRequested.get???()).ifPresent(subscription->{
            
            subscription // do something
            repository().save(subscription);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void checkSubscription(BookViewed bookViewed) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscription subscription = new Subscription();
        repository().save(subscription);

        */

        /** Example 2:  finding and process
        

        repository().findById(bookViewed.get???()).ifPresent(subscription->{
            
            subscription // do something
            repository().save(subscription);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void subscriptionCancel(
        SubscriptionCancelRequested subscriptionCancelRequested
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscription subscription = new Subscription();
        repository().save(subscription);

        */

        /** Example 2:  finding and process
        

        repository().findById(subscriptionCancelRequested.get???()).ifPresent(subscription->{
            
            subscription // do something
            repository().save(subscription);


         });
        */

    }

     @PostPersist
    public void onPostPersist() {
        // 구독이 생성될 때 SubscriptionCreated 이벤트를 발행할 수 있습니다.
        SubscriptionCreated subscriptionCreated = new SubscriptionCreated(this);
        subscriptionCreated.publishAfterCommit();
    }

    // --- [추가] 구독을 취소하는 비즈니스 로직 ---
    /**
     * PolicyHandler에 의해 호출되어 구독을 취소 처리하는 메서드
     */
    public void cancel() {
        // 1. 비즈니스 규칙: 이미 취소된 구독인지 확인
        if ("CANCELED".equals(this.getSubscriptionStatus())) {
            throw new IllegalStateException("이미 취소된 구독입니다.");
        }

        // 2. 상태 변경: 구독 상태를 "CANCELED"로 변경
        this.setSubscriptionStatus("CANCELED");

        // 3. 'SubscriptionCanceled' 이벤트를 발행하여 다른 서비스에 알림
        SubscriptionCanceled subscriptionCanceled = new SubscriptionCanceled(this);
        subscriptionCanceled.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root

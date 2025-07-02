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
    SubscriptionRepository subscriptionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionRequested'"
    )
    public void wheneverSubscriptionRequested_Subscribe(
        @Payload SubscriptionRequested subscriptionRequested
    ) {
        SubscriptionRequested event = subscriptionRequested;
        System.out.println(
            "\n\n##### listener Subscribe : " + subscriptionRequested + "\n\n"
        );

        // Sample Logic //
        Subscription.subscribe(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookViewed'"
    )
    public void wheneverBookViewed_CheckSubscription(
        @Payload BookViewed bookViewed
    ) {
        BookViewed event = bookViewed;
        System.out.println(
            "\n\n##### listener CheckSubscription : " + bookViewed + "\n\n"
        );

        // Sample Logic //
        Subscription.checkSubscription(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionCancelRequested'"
    )
    public void wheneverSubscriptionCancelRequested_SubscriptionCancel(
        @Payload SubscriptionCancelRequested subscriptionCancelRequested
    ) {
        SubscriptionCancelRequested event = subscriptionCancelRequested;
        System.out.println(
            "\n\n##### listener SubscriptionCancel : " +
            subscriptionCancelRequested +
            "\n\n"
        );

        // Sample Logic //
        Subscription.subscriptionCancel(event);
    }
}
//>>> Clean Arch / Inbound Adaptor

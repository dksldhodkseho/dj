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
}
//>>> Clean Arch / Inbound Adaptor

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
    WriterRepository writerRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='WriterRequest'"
    )
    public void wheneverWriterRequest_WriterRequest(
        @Payload WriterRequest writerRequest
    ) {
        WriterRequest event = writerRequest;
        System.out.println(
            "\n\n##### listener WriterRequest : " + writerRequest + "\n\n"
        );

        // Sample Logic //
        Writer.writerRequest(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PublishRequested'"
    )
    public void wheneverPublishRequested_PublishRequest(
        @Payload PublishRequested publishRequested
    ) {
        PublishRequested event = publishRequested;
        System.out.println(
            "\n\n##### listener PublishRequest : " + publishRequested + "\n\n"
        );

        // Sample Logic //
        Writer.publishRequest(event);
    }
}
//>>> Clean Arch / Inbound Adaptor

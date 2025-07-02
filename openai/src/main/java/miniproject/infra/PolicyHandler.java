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
    OpenAiRepository openAiRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CoverGenerationRequested'"
    )
    public void wheneverCoverGenerationRequested_CoverGenerationRequested(
        @Payload CoverGenerationRequested coverGenerationRequested
    ) {
        CoverGenerationRequested event = coverGenerationRequested;
        System.out.println(
            "\n\n##### listener CoverGenerationRequested : " +
            coverGenerationRequested +
            "\n\n"
        );

        // Sample Logic //
        OpenAi.coverGenerationRequested(event);
    }
}
//>>> Clean Arch / Inbound Adaptor

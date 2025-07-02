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
public class PublicationApprovedManagementViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private PublicationApprovedManagementRepository publicationApprovedManagementRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPublishRequested_then_CREATE_1(
        @Payload PublishRequested publishRequested
    ) {
        try {
            if (!publishRequested.validate()) return;

            // view 객체 생성
            PublicationApprovedManagement publicationApprovedManagement = new PublicationApprovedManagement();
            // view 객체에 이벤트의 Value 를 set 함
            publicationApprovedManagement.setBookId(
                publishRequested.getBookId()
            );
            publicationApprovedManagement.setTitle(publishRequested.getTitle());
            publicationApprovedManagement.setContent(
                publishRequested.getContent()
            );
            publicationApprovedManagement.setCoverUrl(
                publishRequested.getCoverUrl()
            );
            publicationApprovedManagement.setWriterId(
                publishRequested.getWriterId()
            );
            publicationApprovedManagement.setPublishStatus(PENDING);
            // view 레파지 토리에 save
            publicationApprovedManagementRepository.save(
                publicationApprovedManagement
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPubApproved_then_UPDATE_1(
        @Payload PubApproved pubApproved
    ) {
        try {
            if (!pubApproved.validate()) return;
            // view 객체 조회
            Optional<PublicationApprovedManagement> publicationApprovedManagementOptional = publicationApprovedManagementRepository.findByBookId(
                pubApproved.getBookId()
            );

            if (publicationApprovedManagementOptional.isPresent()) {
                PublicationApprovedManagement publicationApprovedManagement = publicationApprovedManagementOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                publicationApprovedManagement.setPublishStatus(APPROVED);
                // view 레파지 토리에 save
                publicationApprovedManagementRepository.save(
                    publicationApprovedManagement
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPubRejected_then_UPDATE_2(
        @Payload PubRejected pubRejected
    ) {
        try {
            if (!pubRejected.validate()) return;
            // view 객체 조회
            Optional<PublicationApprovedManagement> publicationApprovedManagementOptional = publicationApprovedManagementRepository.findByBookId(
                pubRejected.getBookId()
            );

            if (publicationApprovedManagementOptional.isPresent()) {
                PublicationApprovedManagement publicationApprovedManagement = publicationApprovedManagementOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                publicationApprovedManagement.setPublishStatus(REJECTED);
                // view 레파지 토리에 save
                publicationApprovedManagementRepository.save(
                    publicationApprovedManagement
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}

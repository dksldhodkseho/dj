package miniproject.infra;

import java.io.IOException;
import java.util.List;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import miniproject.domain.PublicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PublicationApprovedManagementViewHandler {

    @Autowired
    private PublicationApprovedManagementRepository publicationApprovedManagementRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPublishRequested_then_CREATE_1(
        @Payload PublishRequested publishRequested
    ) {
        try {
            if (!publishRequested.validate()) return;

            PublicationApprovedManagement publicationApprovedManagement = new PublicationApprovedManagement();
            publicationApprovedManagement.setBookId(publishRequested.getBookId());
            publicationApprovedManagement.setTitle(publishRequested.getTitle());
            publicationApprovedManagement.setContent(publishRequested.getContent());
            publicationApprovedManagement.setCoverUrl(publishRequested.getCoverUrl());
            publicationApprovedManagement.setWriterId(publishRequested.getWriterId());
            publicationApprovedManagement.setPublishStatus(PublicationStatus.PENDING.toString());

            publicationApprovedManagementRepository.save(publicationApprovedManagement);
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

            List<PublicationApprovedManagement> publicationApprovedManagementOptional =
                publicationApprovedManagementRepository.findByBookId(pubApproved.getBookId());

            if (!publicationApprovedManagementOptional.isEmpty()) {
                PublicationApprovedManagement publicationApprovedManagement =
                    publicationApprovedManagementOptional.get(0);

                publicationApprovedManagement.setPublishStatus(PublicationStatus.APPROVED.toString());

                publicationApprovedManagementRepository.save(publicationApprovedManagement);
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

            List<PublicationApprovedManagement> publicationApprovedManagementOptional =
                publicationApprovedManagementRepository.findByBookId(pubRejected.getBookId());

            if (!publicationApprovedManagementOptional.isEmpty()) {
                PublicationApprovedManagement publicationApprovedManagement =
                    publicationApprovedManagementOptional.get(0);

                publicationApprovedManagement.setPublishStatus(PublicationStatus.REJECTED.toString());

                publicationApprovedManagementRepository.save(publicationApprovedManagement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

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

    @Autowired
    WriterApprovalManagementRepository writerApprovalManagementRepository;

    @Autowired
    PublicationApprovedManagementRepository publicationApprovedManagementRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {
        // 모든 이벤트를 로깅하거나 디버깅할 때 사용할 수 있는 기본 리스너입니다.
    }

    /**
     * [이벤트 수신] User 서비스에서 WriterRequest 이벤트 발생 시
     * WriterApprovalManagement View에 승인 대기 건을 생성합니다.
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='WriterRequest'"
    )
    public void wheneverWriterRequest_CreateApprovalView(
        @Payload WriterRequest writerRequest
    ) {
        // 이벤트 유효성 검사
        if (!writerRequest.validate()) return;
        
        System.out.println(
            "\n\n##### listener WriterRequest -> CreateApprovalView : " +
            writerRequest.toJson() +
            "\n\n"
        );

        // '작가 승인 관리'를 위한 View 데이터를 생성합니다.
        WriterApprovalManagement approvalView = new WriterApprovalManagement();
        approvalView.setWriterId(writerRequest.getUserId());
        approvalView.setApprovalStatus("PENDING"); // 초기 상태: 승인 대기

        // 생성된 View 데이터를 DB에 저장합니다.
        writerApprovalManagementRepository.save(approvalView);
    }

    /**
     * [이벤트 수신] Book 서비스에서 PublishRequested 이벤트 발생 시
     * PublicationApprovedManagement View에 출간 승인 대기 건을 생성합니다.
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PublishRequested'"
    )
    public void wheneverPublishRequested_CreatePublicationApprovalView(
        @Payload PublishRequested publishRequested
    ) {
        // 이벤트 유효성 검사
        if (!publishRequested.validate()) return;

        System.out.println(
            "\n\n##### listener PublishRequested -> CreatePublicationApprovalView : " +
            publishRequested.toJson() +
            "\n\n"
        );

        // '출간 승인 관리'를 위한 View 데이터를 생성합니다.
        PublicationApprovedManagement publicationView = new PublicationApprovedManagement();
        publicationView.setBookId(publishRequested.getBookId());
        publicationView.setTitle(publishRequested.getTitle());
        publicationView.setWriterId(publishRequested.getWriterId());
        publicationView.setContent(publishRequested.getContent());
        publicationView.setCoverUrl(publishRequested.getCoverUrl());
        publicationView.setPublishStatus("PENDING"); // 초기 상태: 승인 대기

        // 생성된 View 데이터를 DB에 저장합니다.
        publicationApprovedManagementRepository.save(publicationView);
    }
}
//>>> Clean Arch / Inbound Adaptor

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

    // Writer Aggregate를 직접 다룰 일은 현재 Policy에 없으므로 WriterRepository는 제거하거나 그대로 두셔도 됩니다.
    @Autowired
    WriterRepository writerRepository; 

    // '작가 승인 관리' View를 저장하기 위한 Repository를 추가합니다.
    @Autowired
    WriterApprovalManagementRepository writerApprovalManagementRepository;

    // '출간 승인 관리' View를 저장하기 위한 Repository를 추가합니다.
    @Autowired
    PublicationApprovedManagementRepository publicationApprovedManagementRepository;


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}


    /**
     * user 서비스에서 작가 신청(WriterRequest) 이벤트가 발생했을 때 리스너
     * @param writerRequest
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='WriterRequest'"
    )
    public void wheneverWriterRequest_WriterRequest(
        @Payload WriterRequest writerRequest
    ) {
        // 이벤트 수신 로그
        System.out.println(
            "\n\n##### listener WriterRequest : " + writerRequest + "\n\n"
        );

        // --- 로직 구현 --- //
        // 1. 새로운 '작가 승인 관리' View 객체를 생성합니다.
        WriterApprovalManagement approvalView = new WriterApprovalManagement();

        // 2. 이벤트로 전달받은 데이터를 View 객체에 설정합니다.
        // writerRequest 이벤트는 userId를 가지고 있습니다. 이것이 작가 ID가 됩니다.
        approvalView.setWriterId(writerRequest.getUserId());
        approvalView.setApprovalStatus("PENDING"); // 초기 상태는 '승인 대기'

        // 3. Repository를 통해 View 데이터를 DB에 저장합니다.
        writerApprovalManagementRepository.save(approvalView);
    }


    /**
     * book 서비스에서 출간 요청(PublishRequested) 이벤트가 발생했을 때 리스너
     * @param publishRequested
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PublishRequested'"
    )
    public void wheneverPublishRequested_PublishRequest(
        @Payload PublishRequested publishRequested
    ) {
        // 이벤트 수신 로그
        System.out.println(
            "\n\n##### listener PublishRequest : " + publishRequested + "\n\n"
        );

        // --- 로직 구현 --- //
        // 1. 새로운 '출간 승인 관리' View 객체를 생성합니다.
        PublicationApprovedManagement publicationView = new PublicationApprovedManagement();
        
        // 2. 이벤트로 전달받은 데이터를 View 객체에 설정합니다.
        publicationView.setBookId(publishRequested.getBookId());
        publicationView.setTitle(publishRequested.getTitle());
        publicationView.setWriterId(publishRequested.getWriterId());
        publicationView.setContent(publishRequested.getContent());
        publicationView.setCoverUrl(publishRequested.getCoverUrl());
        publicationView.setPublishStatus("PENDING"); // 초기 상태는 '승인 대기'

        // 3. Repository를 통해 View 데이터를 DB에 저장합니다.
        publicationApprovedManagementRepository.save(publicationView);
    }
}
//>>> Clean Arch / Inbound Adaptor

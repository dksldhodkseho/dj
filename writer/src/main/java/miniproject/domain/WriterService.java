// In: writer/src/main/java/{패키지명}/domain/WriterService.java

package miniproject.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class WriterService {

    @Autowired
    WriterRepository writerRepository;

    /**
     * 작가 가입 신청을 처리하는 서비스 메서드
     * @param command 가입에 필요한 정보 (email, nickname 등)
     * @return 생성된 Writer 객체
     */
    public Writer registerWriter(WriterRegisterCommand command) {
        // 1. 새로운 Writer Aggregate(엔티티) 객체를 생성합니다.
        Writer writer = new Writer();
        
        // 2. Aggregate의 register 메서드를 호출하여 비즈니스 로직을 위임합니다.
        // 이 메서드 내부에서 'WriterRegistrationRequested' 이벤트가 발행됩니다.
        writer.register(command);
        
        // 3. 생성된 Writer 객체를 DB에 저장합니다.
        // 이때 저장하는 이유는, 나중에 user 서비스에서 계정 생성이 완료되었다는
        // 응답(Registered 이벤트)을 받았을 때, 어떤 가입 요청에 대한 응답인지
        // 추적하기 위함입니다. email 등을 키로 사용하여 이 Writer 데이터를 찾아 업데이트할 수 있습니다.
        writerRepository.save(writer);
        
        return writer;
    }
    
    /**
     * 작가 신청을 승인합니다.
     * @param writerId 승인할 작가의 ID
     */
    public void approveWriter(Long writerId) throws Exception {
        // ID로 Writer Aggregate를 찾아서 approve() 메서드를 호출합니다.
        writerRepository.findById(writerId).ifPresent(writer -> {
            writer.writerApprove(); // Command 객체 없이 호출하도록 수정
            writerRepository.save(writer);
        });
    }

    /**
     * 작가 신청을 거절합니다.
     * @param writerId 거절할 작가의 ID
     */
    public void rejectWriter(Long writerId) throws Exception {
        writerRepository.findById(writerId).ifPresent(writer -> {
            writer.writerReject();
            writerRepository.save(writer);
        });
    }
    }

    /**
     * 출간 신청을 승인합니다.
     * @param writerId 해당 작가(겸 출간 승인 관리 데이터의 ID)
     * @param command 출간 승인에 필요한 정보 (예: bookId)
     */
    public void approvePublication(Long writerId, PubApproveCommand command) throws Exception {
        writerRepository.findById(writerId).ifPresent(writer -> {
            writer.pubApprove(command);
            writerRepository.save(writer);
        });
    }

    /**
     * 출간 신청을 거절합니다.
     * @param writerId 해당 작가(겸 출간 승인 관리 데이터의 ID)
     * @param command 출간 거절에 필요한 정보 (예: bookId)
     */
    public void rejectPublication(Long writerId, PubRejectCommand command) throws Exception {
        writerRepository.findById(writerId).ifPresent(writer -> {
            writer.pubReject(command);
            writerRepository.save(writer);
        });
    }
}
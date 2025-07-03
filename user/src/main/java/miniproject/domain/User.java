package miniproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniproject.UserApplication;

@Entity
@Table(name = "User_table")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String email;
    private String nickname;
    private String passwordHash;
    private Boolean writerRequested;
    
    // --- [수정 1] role 필드 추가 ---
    /**
     * 사용자의 역할 (예: "USER", "WRITER")
     * 작가 승인 시 이 필드가 업데이트됩니다.
     */
    private String role;
    // --- 필드 추가 끝 ---

    public static UserRepository repository() {
        UserRepository userRepository = UserApplication.applicationContext.getBean(
            UserRepository.class
        );
        return userRepository;
    }

    public void register(RegisterCommand registerCommand) {
        // 이 메서드는 작가 신청과 무관하므로 그대로 둡니다.
        Registered registered = new Registered(this);
        registered.publishAfterCommit();
        this.setEmail(registerCommand.getEmail());
        this.setNickname(registerCommand.getNickname());
        this.setPasswordHash(passwordEncoder.encode(registerCommand.getPassword())); // 비밀번호 암호화

        // *** 여기에 역할(Role) 초기값 설정 로직 추가 ***
        this.setRole("USER"); 

        Registered registered = new Registered(this);
        registered.publishAfterCommit();
    }

    public void subscribe(SubscribeCommand subscribeCommand) {
        // 이 메서드는 작가 신청과 무관하므로 그대로 둡니다.
        SubscriptionRequested subscriptionRequested = new SubscriptionRequested(this);
        subscriptionRequested.publishAfterCommit();
    }

    public void writerQuest(WriterQuestCommand writerQuestCommand) {
        // 보내주신 코드가 정확하므로 그대로 유지합니다.
        // 비즈니스 규칙: 이미 신청한 경우 다시 신청할 수 없음
        if (this.writerRequested != null && this.writerRequested) {
            throw new IllegalStateException("이미 작가 신청을 하셨습니다.");
        }

        // 상태 변경: 작가 신청 상태를 true로 변경
        this.setWriterRequested(true);
        
        // 이벤트 발행
        WriterRequest writerRequest = new WriterRequest(this);
        writerRequest.publishAfterCommit();
    }

    // --- [수정 2] becomeWriter 메서드 추가 ---
    /**
     * PolicyHandler에 의해 호출되어 사용자의 역할을 'WRITER'로 승격시키는 메서드
     */
    public void becomeWriter() {
        // 상태 변경: 역할을 'WRITER'로 설정
        this.setRole("WRITER");
        // 이 변경사항은 PolicyHandler에서 save()를 통해 DB에 반영됩니다.
    }
    // --- 메서드 추가 끝 ---

    public void cancelSubscription(CancelSubscriptionCommand cancelSubscriptionCommand) {
        // "이 사용자가 구독을 취소하려고 합니다" 라는 요청 이벤트를 발행합니다.
        SubscriptionCancelRequested subscriptionCancelRequested = new SubscriptionCancelRequested(this);
        subscriptionCancelRequested.publishAfterCommit();
    }

    public void chargePoint(ChargePointCommand chargePointCommand) {
        // "이 사용자에게 이만큼의 포인트를 충전해주세요" 라는 요청 이벤트를 발행합니다.
        PointChargeRequested pointChargeRequested = new PointChargeRequested(this);
        pointChargeRequested.setAmount(chargePointCommand.getAmount());
        pointChargeRequested.publishAfterCommit();
    }

    public boolean checkPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.passwordHash);
    }
}
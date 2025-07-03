// In: user/src/main/java/miniproject/domain/UserService.java
package miniproject.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider; // JWT 생성기 (아래에서 생성)

    /**
     * 회원가입 로직
     */
    public User registerUser(RegisterCommand command) {
        User user = new User();
        user.register(command, passwordEncoder); // 암호화 로직을 포함한 register 호출
        userRepository.save(user); // 저장 시 @PostPersist로 Registered 이벤트 발행
        return user;
    }

    /**
     * 로그인 로직
     * @return JWT 문자열
     */
    public String login(LoginCommand command) {
        // 1. 이메일로 사용자를 찾습니다. 없으면 예외 발생.
        User user = userRepository.findByEmail(command.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        // 2. User Aggregate의 checkPassword 메서드를 통해 비밀번호 일치 여부 확인
        if (!user.checkPassword(command.getPassword(), passwordEncoder)) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 3. 비밀번호가 일치하면, JWT를 생성하여 반환합니다.
        return jwtTokenProvider.createToken(user.getUserId().toString(), user.getRole());
    }
}
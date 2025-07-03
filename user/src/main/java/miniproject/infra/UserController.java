package miniproject.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/users")
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(
        value = "/users/{id}/register",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User register(
        @PathVariable(value = "id") Long id,
        @RequestBody RegisterCommand registerCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/register  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.register(registerCommand);

        userRepository.save(user);
        return user;
    }

    @RequestMapping(
        value = "/users/{id}/subscribe",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User subscribe(
        @PathVariable(value = "id") Long id,
        @RequestBody SubscribeCommand subscribeCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/subscribe  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.subscribe(subscribeCommand);

        userRepository.save(user);
        return user;
    }

    @RequestMapping(
        value = "/users/{id}/writerquest",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User writerQuest(
        @PathVariable(value = "id") Long id,
        @RequestBody WriterQuestCommand writerQuestCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/writerQuest  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.writerQuest(writerQuestCommand);

        userRepository.save(user);
        return user;
    }

    @RequestMapping(
        value = "/users/{id}/cancelsubscription",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User cancelSubscription(
        @PathVariable(value = "id") Long id,
        @RequestBody CancelSubscriptionCommand cancelSubscriptionCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/cancelSubscription  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.cancelSubscription(cancelSubscriptionCommand);

        userRepository.save(user);
        return user;
    }

    @RequestMapping(
        value = "/users/{id}/chargepoint",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User chargePoint(
        @PathVariable(value = "id") Long id,
        @RequestBody ChargePointCommand chargePointCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/chargePoint  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.chargePoint(chargePointCommand);

        userRepository.save(user);
        return user;
    }

     /**
     * 일반 사용자 회원가입 API
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterCommand command) {
        User savedUser = userService.registerUser(command);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * [추가] 로그인 API
     * @param command 로그인 정보 (email, password)
     * @return 성공 시 JWT가 담긴 응답
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginCommand command) {
        // 1. UserService의 login 메서드를 호출하여 JWT를 받아옵니다.
        String token = userService.login(command);

        // 2. JWT를 JSON 형태({"token": "..."})로 감싸서 반환합니다.
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    /**
     * [추가] 로그인 실패 등 IllegalArgumentException 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
//>>> Clean Arch / Inbound Adaptor

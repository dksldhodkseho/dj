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
equestMapping(value="/writers")
@Transactional
public class WriterController {

    @Autowired
    WriterService writerService; // Repository 대신 Service를 주입합니다.

    /**
     * 작가 가입 신청 API
     * @param command 가입 정보를 담은 Request Body
     */
    @PostMapping("/register")
    public ResponseEntity<Writer> registerWriter(@RequestBody WriterRegisterCommand command) throws Exception {
        System.out.println("##### /writers/register called #####");
        
        // Controller는 요청을 받아 Service에 전달하는 역할만 합니다.
        Writer savedWriter = writerService.registerWriter(command);
        
        // 성공적으로 처리되었음을 알리고, 생성된 writer 정보를 응답합니다.
        return ResponseEntity.ok(savedWriter);
    }

    @PutMapping(value = "/{id}/writerapprove")
    public ResponseEntity<Void> writerApprove(@PathVariable(value = "id") Long id) throws Exception {
        System.out.println("##### /writers/" + id + "/writerapprove called #####");
        writerService.approveWriter(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}/writerreject")
    public ResponseEntity<Void> writerReject(@PathVariable(value = "id") Long id) throws Exception {
        System.out.println("##### /writers/" + id + "/writerreject called #####");
        writerService.rejectWriter(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}/pubapprove")
    public ResponseEntity<Void> pubApprove(@PathVariable(value = "id") Long id, @RequestBody PubApproveCommand command) throws Exception {
        System.out.println("##### /writers/" + id + "/pubapprove called #####");
        writerService.approvePublication(id, command);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}/pubreject")
    public ResponseEntity<Void> pubReject(@PathVariable(value = "id") Long id, @RequestBody PubRejectCommand command) throws Exception {
        System.out.println("##### /writers/" + id + "/pubreject called #####");
        writerService.rejectPublication(id, command);
        return ResponseEntity.ok().build();
    }
}
//>>> Clean Arch / Inbound Adaptor

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
// @RequestMapping(value="/writers")
@Transactional
public class WriterController {

    @Autowired
    WriterRepository writerRepository;

    @RequestMapping(
        value = "/writers/{id}/writerapprove",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Writer writerApprove(
        @PathVariable(value = "id") Long id,
        @RequestBody WriterApproveCommand writerApproveCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /writer/writerApprove  called #####");
        Optional<Writer> optionalWriter = writerRepository.findById(id);

        optionalWriter.orElseThrow(() -> new Exception("No Entity Found"));
        Writer writer = optionalWriter.get();
        writer.writerApprove(writerApproveCommand);

        writerRepository.save(writer);
        return writer;
    }

    @RequestMapping(
        value = "/writers/{id}/writerreject",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Writer writerReject(
        @PathVariable(value = "id") Long id,
        @RequestBody WriterRejectCommand writerRejectCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /writer/writerReject  called #####");
        Optional<Writer> optionalWriter = writerRepository.findById(id);

        optionalWriter.orElseThrow(() -> new Exception("No Entity Found"));
        Writer writer = optionalWriter.get();
        writer.writerReject(writerRejectCommand);

        writerRepository.save(writer);
        return writer;
    }

    @RequestMapping(
        value = "/writers/{id}/pubapprove",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Writer pubApprove(
        @PathVariable(value = "id") Long id,
        @RequestBody PubApproveCommand pubApproveCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /writer/pubApprove  called #####");
        Optional<Writer> optionalWriter = writerRepository.findById(id);

        optionalWriter.orElseThrow(() -> new Exception("No Entity Found"));
        Writer writer = optionalWriter.get();
        writer.pubApprove(pubApproveCommand);

        writerRepository.save(writer);
        return writer;
    }

    @RequestMapping(
        value = "/writers/{id}/pubreject",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Writer pubReject(
        @PathVariable(value = "id") Long id,
        @RequestBody PubRejectCommand pubRejectCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /writer/pubReject  called #####");
        Optional<Writer> optionalWriter = writerRepository.findById(id);

        optionalWriter.orElseThrow(() -> new Exception("No Entity Found"));
        Writer writer = optionalWriter.get();
        writer.pubReject(pubRejectCommand);

        writerRepository.save(writer);
        return writer;
    }
}
//>>> Clean Arch / Inbound Adaptor

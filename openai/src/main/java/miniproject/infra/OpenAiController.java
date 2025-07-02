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
// @RequestMapping(value="/openAis")
@Transactional
public class OpenAiController {

    @Autowired
    OpenAiRepository openAiRepository;

    @RequestMapping(
        value = "/openAis/{id}/bookcovercreate",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public OpenAi bookCoverCreate(
        @PathVariable(value = "id") Long id,
        @RequestBody BookCoverCreateCommand bookCoverCreateCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /openAi/bookCoverCreate  called #####");
        Optional<OpenAi> optionalOpenAi = openAiRepository.findById(id);

        optionalOpenAi.orElseThrow(() -> new Exception("No Entity Found"));
        OpenAi openAi = optionalOpenAi.get();
        openAi.bookCoverCreate(bookCoverCreateCommand);

        openAiRepository.save(openAi);
        return openAi;
    }
}
//>>> Clean Arch / Inbound Adaptor

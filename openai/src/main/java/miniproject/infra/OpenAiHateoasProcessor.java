package miniproject.infra;

import miniproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class OpenAiHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<OpenAi>> {

    @Override
    public EntityModel<OpenAi> process(EntityModel<OpenAi> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() + "/bookcovercreate"
                )
                .withRel("bookcovercreate")
        );

        return model;
    }
}

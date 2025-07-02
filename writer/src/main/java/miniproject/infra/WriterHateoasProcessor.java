package miniproject.infra;

import miniproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class WriterHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Writer>> {

    @Override
    public EntityModel<Writer> process(EntityModel<Writer> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/writerapprove")
                .withRel("writerapprove")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/writerreject")
                .withRel("writerreject")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/pubapprove")
                .withRel("pubapprove")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/pubreject")
                .withRel("pubreject")
        );

        return model;
    }
}

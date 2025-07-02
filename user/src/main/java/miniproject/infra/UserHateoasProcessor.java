package miniproject.infra;

import miniproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<User>> {

    @Override
    public EntityModel<User> process(EntityModel<User> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/register")
                .withRel("register")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/subscribe")
                .withRel("subscribe")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/writerquest")
                .withRel("writerquest")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/cancelsubscription"
                )
                .withRel("cancelsubscription")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/chargepoint")
                .withRel("chargepoint")
        );

        return model;
    }
}

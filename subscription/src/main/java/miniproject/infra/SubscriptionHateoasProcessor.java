package miniproject.infra;

import miniproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Subscription>> {

    @Override
    public EntityModel<Subscription> process(EntityModel<Subscription> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/checksubscription"
                )
                .withRel("checksubscription")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/subscriptionregister"
                )
                .withRel("subscriptionregister")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/subscriptioncancel"
                )
                .withRel("subscriptioncancel")
        );

        return model;
    }
}

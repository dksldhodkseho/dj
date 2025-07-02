package miniproject.infra;

import miniproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class BestSellerHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<BestSeller>> {

    @Override
    public EntityModel<BestSeller> process(EntityModel<BestSeller> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/increasebookview"
                )
                .withRel("increasebookview")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/selectbestseller"
                )
                .withRel("selectbestseller")
        );

        return model;
    }
}

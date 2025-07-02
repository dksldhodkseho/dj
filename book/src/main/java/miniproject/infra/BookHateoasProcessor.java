package miniproject.infra;

import miniproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class BookHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Book>> {

    @Override
    public EntityModel<Book> process(EntityModel<Book> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/write")
                .withRel("write")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/delete")
                .withRel("delete")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/publishrequest")
                .withRel("publishrequest")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/viewbook")
                .withRel("viewbook")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() + "/selectbookcover"
                )
                .withRel("selectbookcover")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/requestcovergeneration"
                )
                .withRel("requestcovergeneration")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/update")
                .withRel("update")
        );

        return model;
    }
}

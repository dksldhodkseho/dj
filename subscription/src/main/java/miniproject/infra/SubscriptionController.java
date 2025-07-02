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
// @RequestMapping(value="/subscriptions")
@Transactional
public class SubscriptionController {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @RequestMapping(
        value = "/subscriptions/{id}/checksubscription",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Subscription checkSubscription(
        @PathVariable(value = "id") Long id,
        @RequestBody CheckSubscriptionCommand checkSubscriptionCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /subscription/checkSubscription  called #####"
        );
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(
            id
        );

        optionalSubscription.orElseThrow(() -> new Exception("No Entity Found")
        );
        Subscription subscription = optionalSubscription.get();
        subscription.checkSubscription(checkSubscriptionCommand);

        subscriptionRepository.save(subscription);
        return subscription;
    }

    @RequestMapping(
        value = "/subscriptions/{id}/subscriptionregister",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Subscription subscriptionRegister(
        @PathVariable(value = "id") Long id,
        @RequestBody SubscriptionRegisterCommand subscriptionRegisterCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /subscription/subscriptionRegister  called #####"
        );
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(
            id
        );

        optionalSubscription.orElseThrow(() -> new Exception("No Entity Found")
        );
        Subscription subscription = optionalSubscription.get();
        subscription.subscriptionRegister(subscriptionRegisterCommand);

        subscriptionRepository.save(subscription);
        return subscription;
    }

    @RequestMapping(
        value = "/subscriptions/{id}/subscriptioncancel",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Subscription subscriptionCancel(
        @PathVariable(value = "id") Long id,
        @RequestBody SubscriptionCancelCommand subscriptionCancelCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /subscription/subscriptionCancel  called #####"
        );
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(
            id
        );

        optionalSubscription.orElseThrow(() -> new Exception("No Entity Found")
        );
        Subscription subscription = optionalSubscription.get();
        subscription.subscriptionCancel(subscriptionCancelCommand);

        subscriptionRepository.save(subscription);
        return subscription;
    }
}
//>>> Clean Arch / Inbound Adaptor

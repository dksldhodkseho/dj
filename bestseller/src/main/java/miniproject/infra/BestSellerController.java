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
// @RequestMapping(value="/bestSellers")
@Transactional
public class BestSellerController {

    @Autowired
    BestSellerRepository bestSellerRepository;

    @RequestMapping(
        value = "/bestSellers/{id}/increasebookview",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public BestSeller increaseBookView(
        @PathVariable(value = "id") Long id,
        @RequestBody IncreaseBookViewCommand increaseBookViewCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /bestSeller/increaseBookView  called #####");
        Optional<BestSeller> optionalBestSeller = bestSellerRepository.findById(
            id
        );

        optionalBestSeller.orElseThrow(() -> new Exception("No Entity Found"));
        BestSeller bestSeller = optionalBestSeller.get();
        bestSeller.increaseBookView(increaseBookViewCommand);

        bestSellerRepository.save(bestSeller);
        return bestSeller;
    }

    @RequestMapping(
        value = "/bestSellers/{id}/selectbestseller",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public BestSeller selectBestSeller(
        @PathVariable(value = "id") Long id,
        @RequestBody SelectBestSellerCommand selectBestSellerCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /bestSeller/selectBestSeller  called #####");
        Optional<BestSeller> optionalBestSeller = bestSellerRepository.findById(
            id
        );

        optionalBestSeller.orElseThrow(() -> new Exception("No Entity Found"));
        BestSeller bestSeller = optionalBestSeller.get();
        bestSeller.selectBestSeller(selectBestSellerCommand);

        bestSellerRepository.save(bestSeller);
        return bestSeller;
    }
}
//>>> Clean Arch / Inbound Adaptor

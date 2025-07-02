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
// @RequestMapping(value="/points")
@Transactional
public class PointController {

    @Autowired
    PointRepository pointRepository;

    @RequestMapping(
        value = "/points/{id}/deductpoint",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Point deductPoint(
        @PathVariable(value = "id") Long id,
        @RequestBody DeductPointCommand deductPointCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /point/deductPoint  called #####");
        Optional<Point> optionalPoint = pointRepository.findById(id);

        optionalPoint.orElseThrow(() -> new Exception("No Entity Found"));
        Point point = optionalPoint.get();
        point.deductPoint(deductPointCommand);

        pointRepository.save(point);
        return point;
    }

    @RequestMapping(
        value = "/points/{id}/chargepoint",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Point chargePoint(
        @PathVariable(value = "id") Long id,
        @RequestBody ChargePointCommand chargePointCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /point/chargePoint  called #####");
        Optional<Point> optionalPoint = pointRepository.findById(id);

        optionalPoint.orElseThrow(() -> new Exception("No Entity Found"));
        Point point = optionalPoint.get();
        point.chargePoint(chargePointCommand);

        pointRepository.save(point);
        return point;
    }
}
//>>> Clean Arch / Inbound Adaptor

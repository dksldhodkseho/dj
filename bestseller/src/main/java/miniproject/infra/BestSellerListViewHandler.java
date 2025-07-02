package miniproject.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class BestSellerListViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private BestSellerListRepository bestSellerListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBestsellerSelected_then_CREATE_1(
        @Payload BestsellerSelected bestsellerSelected
    ) {
        try {
            if (!bestsellerSelected.validate()) return;

            // view 객체 생성
            BestSellerList bestSellerList = new BestSellerList();
            // view 객체에 이벤트의 Value 를 set 함
            bestSellerList.setBookId(
                Long.valueOf(bestsellerSelected.getBookId())
            );
            bestSellerList.setTitle(bestsellerSelected.getTitle());
            bestSellerList.setCoverUrl(bestsellerSelected.getCoverUrl());
            bestSellerList.setViewCount(bestsellerSelected.getViewCount());
            bestSellerList.setWriterId(bestsellerSelected.getWriterId());
            bestSellerList.setSelectedStatus(
                bestsellerSelected.getSelectedStatus()
            );
            bestSellerList.setSelectedAt(bestsellerSelected.getSelectedAt());
            // view 레파지 토리에 save
            bestSellerListRepository.save(bestSellerList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookViewIncreased_then_UPDATE_1(
        @Payload BookViewIncreased bookViewIncreased
    ) {
        try {
            if (!bookViewIncreased.validate()) return;
            // view 객체 조회
            Optional<BestSellerList> bestSellerListOptional = bestSellerListRepository.findByBookId(
                bookViewIncreased.getBookId()
            );

            if (bestSellerListOptional.isPresent()) {
                BestSellerList bestSellerList = bestSellerListOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                bestSellerList.setViewCount(bookViewIncreased.getViewCount());
                // view 레파지 토리에 save
                bestSellerListRepository.save(bestSellerList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}

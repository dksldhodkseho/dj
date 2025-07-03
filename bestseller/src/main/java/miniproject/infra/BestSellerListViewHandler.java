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

    @Autowired
    private BestSellerListRepository bestSellerListRepository;

    /**
     * '베스트셀러 선정됨' 이벤트가 발생하면, 이 정보를 View 테이블에 생성(CREATE)합니다.
     * 이 메서드는 스케줄링 작업이 끝난 후에만 호출됩니다.
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BestsellerSelected'"
    )
    public void whenBestsellerSelected_then_CREATE_1(
        @Payload BestsellerSelected bestsellerSelected
    ) {
        try {
            if (!bestsellerSelected.validate()) return;

            // view 객체 생성
            BestSellerList bestSellerList = new BestSellerList();
            
            // view 객체에 이벤트의 Value를 set 함
            // bookId 타입을 Long으로 통일했다고 가정하고 Long.valueOf() 제거
            bestSellerList.setBookId(bestsellerSelected.getBookId());
            bestSellerList.setTitle(bestsellerSelected.getTitle());
            bestSellerList.setCoverUrl(bestsellerSelected.getCoverUrl());
            bestSellerList.setViewCount(bestsellerSelected.getViewCount());
            bestSellerList.setWriterId(bestsellerSelected.getWriterId());
            bestSellerList.setSelectedStatus(
                bestsellerSelected.getSelectedStatus()
            );
            bestSellerList.setSelectedAt(bestsellerSelected.getSelectedAt());
            bestSellerList.setRank(bestsellerSelected.getRank()); // 랭킹 정보 추가
            
            // view 레파지토리에 save
            bestSellerListRepository.save(bestSellerList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // BookViewIncreased 리스너는 PolicyHandler가 담당하므로 여기서 삭제합니다.
}

// In: bestseller/src/main/java/{패키지명}/domain/BestsellerBatchService.java
package miniproject.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BestsellerBatchService {

    @Autowired
    BookViewCountRepository bookViewCountRepository;
    
    @Autowired
    BestSellerRepository bestSellerRepository;

    @Autowired
    BestSellerListRepository bestSellerListRepository;

    /**
     * 매일 새벽 1시에 실행되는 스케줄러 (cron = "초 분 시 일 월 요일")
     * "0 0 1 * * *" = 매일 1시 0분 0초
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void selectBestSellers() {
        System.out.println("##### 베스트셀러 선정 배치 작업 시작 #####");

        // 1. 기존 베스트셀러 목록을 모두 삭제
        bestSellerRepository.deleteAll();
        bestSellerListRepository.deleteAll();

        // 2. 조회수(viewCount)가 가장 높은 10개의 책을 조회
        List<BookViewCount> topBooks = bookViewCountRepository.findTop10ByOrderByViewCountDesc();

        int rank = 1;
        for (BookViewCount book : topBooks) {
            // 3. 새로운 BestSeller 엔티티를 생성
            BestSeller bestSeller = new BestSeller();
            bestSeller.setBookId(book.getBookId());
            bestSeller.setTitle(book.getTitle());
            bestSeller.setRank(rank++);
            
            // 4. DB에 저장 (저장 시 BestSeller Aggregate의 @PostPersist가 있다면 이벤트 발행됨)
            bestSellerRepository.save(bestSeller);
        }
        
        System.out.println("##### 베스트셀러 선정 배치 작업 완료 #####");
    }
}
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
    // In BestsellerBatchService.java

@Scheduled(cron = "0 0 1 * * *")
public void selectBestSellers() {
    System.out.println("##### 베스트셀러 선정 배치 작업 시작 #####");

    bestSellerRepository.deleteAll();
    bestSellerListRepository.deleteAll();

    List<BookViewCount> topBooks = bookViewCountRepository.findTop10ByOrderByViewCountDesc();

    int rank = 1;
    for (BookViewCount book : topBooks) {
        BestSeller bestSeller = new BestSeller();
        
        // --- [수정] Long 타입을 String으로 변환 ---
        bestSeller.setBookId(String.valueOf(book.getBookId()));
        // ------------------------------------

        bestSeller.setTitle(book.getTitle());
        bestSeller.setRank(rank++);
        bestSeller.setViewCount(book.getViewCount().intValue()); // Long to Integer
        
        bestSellerRepository.save(bestSeller);
    }
    
    System.out.println("##### 베스트셀러 선정 배치 작업 완료 #####");
}
}
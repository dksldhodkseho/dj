package miniproject.domain;

import java.util.Date;
import java.util.List;
import miniproject.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "subscriptions",
    path = "subscriptions"
)
public interface SubscriptionRepository
    extends PagingAndSortingRepository<Subscription, Long> {
    // --- 아래 메서드를 추가합니다 ---
    /**
     * userId와 subscriptionStatus로 구독 정보를 조회하는 메서드
     */
    Optional<Subscription> findByUserIdAndSubscriptionStatus(Long userId, String status);
    // -------------------------
    }

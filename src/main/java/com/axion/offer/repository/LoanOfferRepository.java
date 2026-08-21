package com.axion.offer.repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.axion.offer.entity.LoanOffer;
import com.axion.offer.entity.LoanOfferStatus;
public interface LoanOfferRepository extends JpaRepository<LoanOffer, UUID> {
 List<LoanOffer> findByBorrowingRequestId(UUID requestId);
 List<LoanOffer> findByStatusAndExpiresAtBefore(LoanOfferStatus status, LocalDateTime expiresAt);
}

package com.axion.matching.repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.axion.matching.entity.LenderMatch;
public interface LenderMatchRepository extends JpaRepository<LenderMatch, UUID> { List<LenderMatch> findByBorrowingRequestId(UUID requestId); }

package com.axion.borrowing.repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.axion.borrowing.entity.BorrowingRequest;
public interface BorrowingRequestRepository extends JpaRepository<BorrowingRequest, UUID> { List<BorrowingRequest> findByBorrowerId(UUID borrowerId); }

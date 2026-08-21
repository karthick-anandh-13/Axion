package com.axion.borrowing.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.authentication.entity.User;
import com.axion.authentication.repository.UserRepository;
import com.axion.borrowing.dto.BorrowingRequestResponse;
import com.axion.borrowing.dto.CreateBorrowingRequest;
import com.axion.borrowing.entity.BorrowingRequest;
import com.axion.borrowing.repository.BorrowingRequestRepository;
import com.axion.borrowing.service.BorrowingRequestService;

@Service
@Transactional
public class BorrowingRequestServiceImpl
        implements BorrowingRequestService {

    private final BorrowingRequestRepository borrowingRequestRepository;
    private final UserRepository userRepository;

    public BorrowingRequestServiceImpl(
            BorrowingRequestRepository borrowingRequestRepository,
            UserRepository userRepository) {

        this.borrowingRequestRepository = borrowingRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BorrowingRequestResponse createRequest(
            UUID borrowerId,
            CreateBorrowingRequest request) {

        User borrower = userRepository.findById(borrowerId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Borrower not found"));

        BorrowingRequest borrowingRequest = BorrowingRequest.builder()
                .borrower(borrower)
                .purpose(request.purpose())
                .purposeDescription(request.purposeDescription())
                .requestedAmount(request.requestedAmount())
                .requestedTenureMonths(request.requestedTenureMonths())
                .maximumAcceptableApr(request.maximumAcceptableApr())
                .build();

        BorrowingRequest saved =
                borrowingRequestRepository.save(borrowingRequest);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BorrowingRequestResponse getRequest(UUID requestId) {

        BorrowingRequest request = borrowingRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Borrowing request not found"));

        return toResponse(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowingRequestResponse> getBorrowerRequests(UUID borrowerId) {

        return borrowingRequestRepository.findByBorrowerId(borrowerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public BorrowingRequestResponse submitRequest(
            UUID requestId,
            UUID borrowerId) {

        BorrowingRequest request = borrowingRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Borrowing request not found"));

        if (!Objects.equals(request.getBorrower().getId(), borrowerId)) {
            throw new IllegalArgumentException("You are not the owner of this request");
        }

        if (!"DRAFT".equals(request.getStatus().name())) {
            throw new IllegalStateException("Request has already been submitted");
        }

        request.setStatus(
                Enum.valueOf(
                        request.getStatus().getDeclaringClass(),
                        "SUBMITTED"
                )
        );

        BorrowingRequest saved = borrowingRequestRepository.save(request);

        return toResponse(saved);
    }

    private BorrowingRequestResponse toResponse(BorrowingRequest request) {

        return new BorrowingRequestResponse(
                request.getId(),
                request.getBorrower().getId(),
                request.getPurpose(),
                request.getPurposeDescription(),
                request.getRequestedAmount(),
                request.getRequestedTenureMonths(),
                request.getMaximumAcceptableApr(),
                request.getStatus(),
                request.getCreatedAt(),
                request.getUpdatedAt()
        );
    }
}
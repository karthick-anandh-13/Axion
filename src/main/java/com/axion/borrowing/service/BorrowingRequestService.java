package com.axion.borrowing.service;

import java.util.List;
import java.util.UUID;

import com.axion.borrowing.dto.BorrowingRequestResponse;
import com.axion.borrowing.dto.CreateBorrowingRequest;

public interface BorrowingRequestService {

    BorrowingRequestResponse createRequest(
            UUID borrowerId,
            CreateBorrowingRequest request
    );

    BorrowingRequestResponse getRequest(
            UUID requestId
    );

    List<BorrowingRequestResponse> getBorrowerRequests(
            UUID borrowerId
    );

    BorrowingRequestResponse submitRequest(
            UUID requestId,
            UUID borrowerId
    );
}
package com.axion.borrowing.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.axion.borrowing.dto.BorrowingRequestResponse;
import com.axion.borrowing.dto.CreateBorrowingRequest;
import com.axion.borrowing.service.BorrowingRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/borrowing/requests")
@Validated
public class BorrowingRequestController {

    private final BorrowingRequestService borrowingRequestService;

    public BorrowingRequestController(
            BorrowingRequestService borrowingRequestService) {
        this.borrowingRequestService = borrowingRequestService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BorrowingRequestResponse> createRequest(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateBorrowingRequest request) {

        UUID borrowerId = UUID.fromString(jwt.getSubject());

        BorrowingRequestResponse response =
                borrowingRequestService.createRequest(borrowerId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BorrowingRequestResponse>> getMyRequests(
            @AuthenticationPrincipal Jwt jwt) {

        UUID borrowerId = UUID.fromString(jwt.getSubject());

        return ResponseEntity.ok(
                borrowingRequestService.getBorrowerRequests(borrowerId)
        );
    }

    @GetMapping("/{requestId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BorrowingRequestResponse> getRequest(
            @PathVariable UUID requestId) {

        return ResponseEntity.ok(
                borrowingRequestService.getRequest(requestId)
        );
    }

    @PostMapping("/{requestId}/submit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BorrowingRequestResponse> submitRequest(
            @PathVariable UUID requestId,
            @AuthenticationPrincipal Jwt jwt) {

        UUID borrowerId = UUID.fromString(jwt.getSubject());

        BorrowingRequestResponse response =
                borrowingRequestService.submitRequest(requestId, borrowerId);

        return ResponseEntity.ok(response);
    }
}
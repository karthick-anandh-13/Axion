package com.axion.borrowing.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.axion.borrowing.limit.BorrowingLimitEngine;
import com.axion.borrowing.limit.BorrowingLimitResult;

@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowingLimitController {

    private final BorrowingLimitEngine borrowingLimitEngine;

    public BorrowingLimitController(
            BorrowingLimitEngine borrowingLimitEngine) {

        this.borrowingLimitEngine =
                borrowingLimitEngine;
    }

    @GetMapping("/{borrowerId}/limit")
    public ResponseEntity<BorrowingLimitResult>
    getBorrowingLimit(
            @PathVariable UUID borrowerId) {

        return ResponseEntity.ok(
                borrowingLimitEngine.calculateLimit(
                        borrowerId
                )
        );
    }
}
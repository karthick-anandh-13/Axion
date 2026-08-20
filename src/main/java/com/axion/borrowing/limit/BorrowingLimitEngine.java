package com.axion.borrowing.limit;

import java.util.UUID;

public interface BorrowingLimitEngine {

    BorrowingLimitResult calculateLimit(
            UUID borrowerId
    );
}
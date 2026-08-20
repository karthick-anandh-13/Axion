package com.axion.matching.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record MatchResponse(

        UUID partnerId,

        String organizationName,

        Double score,

        BigDecimal offeredApr,

        BigDecimal availableCapital

) {
}
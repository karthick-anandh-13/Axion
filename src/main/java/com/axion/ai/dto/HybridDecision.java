package com.axion.ai.dto;

import java.util.List;

public record HybridDecision(
        String decision,
        double confidence,
        List<String> reasons,
        List<String> warnings
) {
}
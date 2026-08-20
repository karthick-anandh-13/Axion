package com.axion.matching.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.axion.matching.dto.MatchResponse;
import com.axion.matching.engine.MatchingEngine;
import com.axion.matching.service.MatchingService;

@Service
public class MatchingServiceImpl implements MatchingService {

    private final MatchingEngine matchingEngine;

    public MatchingServiceImpl(MatchingEngine matchingEngine) {
        this.matchingEngine = matchingEngine;
    }

    @Override
    public List<MatchResponse> getTopMatches(UUID borrowingRequestId) {
        return matchingEngine.generateMatches(borrowingRequestId);
    }
}
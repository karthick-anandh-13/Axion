package com.axion.matching.service;

import java.util.List;
import java.util.UUID;

import com.axion.matching.dto.MatchResponse;

public interface MatchingService {

    List<MatchResponse> getTopMatches(UUID borrowingRequestId);

}
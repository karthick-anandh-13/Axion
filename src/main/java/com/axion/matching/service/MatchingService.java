package com.axion.matching.service;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;

import com.axion.matching.dto.MatchResponse;

public interface MatchingService {

    @NonNull
    List<MatchResponse> getTopMatches(@NonNull UUID borrowingRequestId);

}
package com.axion.matching.engine;
import java.util.List;
import java.util.UUID;
import com.axion.matching.dto.MatchResponse;
public interface MatchingEngine { List<MatchResponse> generateMatches(UUID borrowingRequestId); }

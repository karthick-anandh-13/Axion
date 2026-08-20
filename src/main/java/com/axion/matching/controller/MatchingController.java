package com.axion.matching.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.axion.matching.dto.MatchResponse;
import com.axion.matching.service.MatchingService;

@RestController
@RequestMapping("/api/v1/matching")
public class MatchingController {

    private final MatchingService matchingService;

    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @GetMapping("/{borrowingRequestId}")
    public ResponseEntity<List<MatchResponse>> getMatches(
            @PathVariable UUID borrowingRequestId) {

        return ResponseEntity.ok(
                matchingService.getTopMatches(borrowingRequestId)
        );
    }
}
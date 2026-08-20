package com.axion.ai.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.ai.dto.GraphRiskResult;
import com.axion.ai.graph.RiskGraphNodeType;
import com.axion.ai.graph.RiskGraphRelationship;
import com.axion.ai.graph.RiskGraphRelationshipRepository;
import com.axion.ai.service.GraphRiskEngine;

@Service
@Transactional(readOnly = true)
public class PostgresGraphRiskEngine
        implements GraphRiskEngine {

    private final RiskGraphRelationshipRepository repository;

    public PostgresGraphRiskEngine(
            RiskGraphRelationshipRepository repository) {

        this.repository = repository;
    }

    @Override
    public GraphRiskResult evaluate(
            UUID customerId) {

        String customerNodeId =
                customerId.toString();

        List<RiskGraphRelationship> relationships =
                repository.findBySourceIdOrTargetId(
                        customerNodeId,
                        customerNodeId
                );

        Set<String> connectedEntities =
                new HashSet<>();

        List<String> riskFactors =
                new ArrayList<>();

        int suspiciousConnections = 0;

        for (RiskGraphRelationship relationship
                : relationships) {

            String connectedId =
                    extractConnectedEntity(
                            relationship,
                            customerNodeId
                    );

            if (connectedId != null) {

                connectedEntities.add(
                        connectedId
                );
            }

            /*
             * Low-confidence relationships are
             * potentially suspicious.
             */
            if (relationship.getConfidence()
                    < 0.50) {

                suspiciousConnections++;

                riskFactors.add(
                        "Low-confidence relationship: "
                                + relationship.getRelationType()
                );
            }
        }

        double riskScore =
                calculateRiskScore(
                        connectedEntities.size(),
                        suspiciousConnections
                );

        String riskLevel =
                determineRiskLevel(
                        riskScore
                );

        String reason =
                riskFactors.isEmpty()
                        ? "No graph-based risk indicators detected."
                        : "Graph-based risk indicators detected.";

        return new GraphRiskResult(
                riskScore,
                riskLevel,
                connectedEntities.size(),
                suspiciousConnections,
                riskFactors,
                reason
        );
    }

    private String extractConnectedEntity(
            RiskGraphRelationship relationship,
            String customerId) {

        if (relationship.getSourceId()
                .equals(customerId)) {

            return relationship.getTargetType()
                    + ":"
                    + relationship.getTargetId();
        }

        if (relationship.getTargetId()
                .equals(customerId)) {

            return relationship.getSourceType()
                    + ":"
                    + relationship.getSourceId();
        }

        return null;
    }

    private double calculateRiskScore(
            int connectedEntities,
            int suspiciousConnections) {

        double score = 0.0;

        /*
         * These are initial heuristics.
         * They will later be replaced/calibrated
         * using actual fraud data.
         */

        if (connectedEntities >= 10) {
            score += 0.20;
        }

        if (connectedEntities >= 25) {
            score += 0.20;
        }

        score +=
                Math.min(
                        suspiciousConnections * 0.10,
                        0.60
                );

        return Math.min(
                score,
                1.0
        );
    }

    private String determineRiskLevel(
            double riskScore) {

        if (riskScore >= 0.80) {
            return "HIGH";
        }

        if (riskScore >= 0.50) {
            return "MEDIUM";
        }

        return "LOW";
    }
}
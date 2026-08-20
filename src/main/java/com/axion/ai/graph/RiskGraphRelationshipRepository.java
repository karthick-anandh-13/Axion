package com.axion.ai.graph;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskGraphRelationshipRepository
        extends JpaRepository<RiskGraphRelationship, UUID> {

    List<RiskGraphRelationship>
    findBySourceTypeAndSourceId(
            RiskGraphNodeType sourceType,
            String sourceId
    );

    List<RiskGraphRelationship>
    findByTargetTypeAndTargetId(
            RiskGraphNodeType targetType,
            String targetId
    );

    List<RiskGraphRelationship>
    findBySourceIdOrTargetId(
            String sourceId,
            String targetId
    );
}
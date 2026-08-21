package com.axion.ai.graph;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
//src\main\java\com\axion\ai\graph\RiskGraphRelationship.java
@Entity
@Table(name = "risk_graph_relationships")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RiskGraphRelationship {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Enumerated(EnumType.STRING) @Column(name = "source_type", nullable = false, length = 30) private RiskGraphNodeType sourceType;
    @Column(name = "source_id", nullable = false) private String sourceId;
    @Enumerated(EnumType.STRING) @Column(name = "target_type", nullable = false, length = 30) private RiskGraphNodeType targetType;
    @Column(name = "target_id", nullable = false) private String targetId;
    @Enumerated(EnumType.STRING) @Column(name = "relation_type", nullable = false, length = 40) private RiskGraphRelationType relationType;
    @Column(nullable = false) private Double confidence;
    @Column(nullable = false, updatable = false) private LocalDateTime createdAt;
    @PrePersist public void prePersist() { createdAt = LocalDateTime.now(); }
}

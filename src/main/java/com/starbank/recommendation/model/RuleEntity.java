package com.starbank.recommendation.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rule_entity")
@Data
public class RuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private UUID productId;
    private String productText;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<OneRuleDto> rule;

    public RuleEntity() {
    }

    public RuleEntity(String productName, UUID productId, String productText, List<OneRuleDto> rules) {
    }

}

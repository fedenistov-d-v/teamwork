package com.starbank.recommendation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;

public record RuleDto(
        @JsonProperty("product_name") String productName,
        @JsonProperty("product_id") UUID productId,
        @JsonProperty("product_text") String productText,
        @JsonProperty("rule") List<OneRuleDto> rules
) {}

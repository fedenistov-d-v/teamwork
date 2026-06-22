package com.starbank.recommendation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record OneRuleDto(
        @JsonProperty("query") String query,
        @JsonProperty("arguments") List<String> arguments,
        @JsonProperty("negate") boolean negate
) {}
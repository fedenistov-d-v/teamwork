package com.starbank.recommendation.component;

import com.starbank.recommendation.model.RecommendationDto;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    public Optional<RecommendationDto> check(UUID user_id);
}

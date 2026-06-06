package com.starbank.recommendation.service.component;

import com.starbank.recommendation.modul.RecommendationDto;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    public Optional<RecommendationDto> check(UUID user_id);
}

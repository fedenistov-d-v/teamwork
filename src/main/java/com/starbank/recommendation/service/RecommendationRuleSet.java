package com.starbank.recommendation.service;

import com.starbank.recommendation.model.RecommendationDto;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
   Optional<RecommendationDto> check(UUID user_id);
}

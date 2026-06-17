package com.starbank.recommendation.rule;

import com.starbank.recommendation.modul.RecommendationDto;

import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс содержит метод содержащий набор правил.
 */
public interface RecommendationRuleSet {
    Optional<RecommendationDto> check(UUID userId);
}

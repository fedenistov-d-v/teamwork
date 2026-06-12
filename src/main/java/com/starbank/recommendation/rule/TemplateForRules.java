package com.starbank.recommendation.rule;

import com.starbank.recommendation.modul.RecommendationDto;

import java.util.Optional;
import java.util.UUID;

public abstract class TemplateForRules implements RecommendationRuleSet {

    private final RecommendationDto recommendation;

    protected TemplateForRules(UUID id, String name, String text) {
        recommendation = new RecommendationDto(id, name, text);
    }

    @Override
    public Optional<RecommendationDto> check(UUID userId) {
        if (isEligible(userId)) {
            return Optional.of(recommendation);
        }
        return Optional.empty();
    }

    protected abstract boolean isEligible(UUID userId);
}

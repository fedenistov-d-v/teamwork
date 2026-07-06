package com.starbank.recommendation.rule;



import com.starbank.recommendation.model.RecommendationDto;

import java.util.Optional;
import java.util.UUID;

public abstract class TemplateForRules implements RecommendationRuleSet {

    private final RecommendationDto recommendation;

    protected TemplateForRules(UUID id, String name, String text) {
        recommendation = new RecommendationDto(id, name, text);
    }

    @Override
    public Optional<RecommendationDto> check(UUID user_id) {
        if (isEligible(user_id)) {
            return Optional.of(recommendation);
        }
        return Optional.empty();
    }

    protected abstract boolean isEligible(UUID userId);
}

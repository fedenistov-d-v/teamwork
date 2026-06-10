package com.starbank.recommendation.service;

import com.starbank.recommendation.model.RecommendationDto;
import com.starbank.recommendation.model.UserRecommendationsDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationService(
            @Qualifier("recommendationInvest500") RecommendationRuleSet ruleSet1,
            @Qualifier("recommendationTopSaving") RecommendationRuleSet ruleSet2,
            @Qualifier("recommendationSimpleCredit") RecommendationRuleSet ruleSet3
    ) {
        this.ruleSets = List.of(ruleSet1, ruleSet2, ruleSet3);
    }

    public UserRecommendationsDto getUserRecommendationsByUserId(UUID uid) {

        List<RecommendationDto> recommendationDto = ruleSets.stream()
                .map(rule -> rule.check(uid))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        return new UserRecommendationsDto(uid, recommendationDto);
    }

}

package com.starbank.recommendation.service;

import com.starbank.recommendation.modul.RecommendationDto;
import com.starbank.recommendation.repository.RecommendationsRepository;
import com.starbank.recommendation.service.component.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationService {
    private final List<RecommendationRuleSet> ruleSets;
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationService(List<RecommendationRuleSet> ruleSets,
                                 RecommendationsRepository recommendationsRepository) {
        this.ruleSets = ruleSets;
        this.recommendationsRepository = recommendationsRepository;
    }

    public List<RecommendationDto> getRecommendationsByIdUsers(UUID user_id) {
//        boolean hasDebit = recommendationsRepository.getRandomTransactionAmount(user_id);
//        System.out.println("hasDebit = " + hasDebit);
        return ruleSets.stream()
                .map(rule -> rule.check(user_id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}

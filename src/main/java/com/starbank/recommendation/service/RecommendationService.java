package com.starbank.recommendation.service;

import com.starbank.recommendation.modul.RecommendationDto;
import com.starbank.recommendation.repository.GeneralQueries;
import com.starbank.recommendation.repository.RecommendationsRepository;
import com.starbank.recommendation.service.component.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationService {
    private final List<RecommendationRuleSet> ruleSets;
    private final RecommendationsRepository generalRepository;

    public RecommendationService(List<RecommendationRuleSet> ruleSets, RecommendationsRepository generalRepository) {
        this.ruleSets = ruleSets;
        this.generalRepository = generalRepository;
    }

    public List<RecommendationDto> getRecommendationsByIdUsers(UUID user_id) {
        calculateGeneralQueries(user_id);
        return ruleSets.stream()
                .map(rule -> rule.check(user_id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    protected void calculateGeneralQueries(UUID user_id) {
        GeneralQueries.hasDebit = generalRepository.getHasDebit(user_id);
        GeneralQueries.hasInvest = generalRepository.getHasInvest(user_id);
        GeneralQueries.hasCredit = generalRepository.getHasCredit(user_id);
        GeneralQueries.sumDebitDeposit = generalRepository.getSumDebitDeposit(user_id);
        GeneralQueries.sumSavingDeposit = generalRepository.getSumSavingDeposit(user_id);
        GeneralQueries.sumDebitWithdrawal = generalRepository.getSumDebitWithdraw(user_id);
    }
}

package com.starbank.recommendation.service;

import com.starbank.recommendation.modul.RecommendationResponseDto;
import com.starbank.recommendation.rule.RecommendationRuleSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис рекомендаций
 * Инжектирует List<RecommendationRuleSet> - список всех рекомендаций банка с условиями.
 */
@Service
public class RecommendationService {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);
    private final List<RecommendationRuleSet> rules;

    public RecommendationService(List<RecommendationRuleSet> rules) {
        this.rules = rules;
    }


    /**
     * Метод определяет какие рекомендации рекомендовать клиенту банка.
     *
     * @param userId - ID клиента банка.
     * @return список рекомендованных рекомендаций.
     */
    @Cacheable(value = "recommendations", key = "#userId")
    public RecommendationResponseDto getRecommendationsByIdUsers(UUID userId) {
        logger.info("Вызван метод для получения рекомендаций с userId = ({})", userId);
        return new RecommendationResponseDto(userId, rules.stream()
                .map(rule -> rule.check(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList());
    }
}

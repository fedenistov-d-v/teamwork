package com.starbank.recommendation.service;

import com.starbank.recommendation.model.*;
import com.starbank.recommendation.model.enums.QueryType;
import com.starbank.recommendation.repository.RuleRepository;
import com.starbank.recommendation.rule.RuleCheck;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис рекомендаций
 * Инжектирует List<RuleCheck> - все объекты по проверке правил рекомендаций
 */
@Service
@Slf4j
public class RecommendationService {

    private final RuleRepository ruleRepository;

    @Autowired
    private List<RuleCheck> ruleChecks;

    private final Map<QueryType, RuleCheck> ruleCheckMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for (RuleCheck ruleCheck : ruleChecks) {
            ruleCheckMap.put(ruleCheck.getQueryType(), ruleCheck);
        }
    }

    public RecommendationService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    /**
     * Метод выдает рекомендации клиенту банка в зависимости от истории его операций в банке
     *
     * @param user_id - ID клиента банка.
     * @return массив рекомендаций для этого пользователя
     */
    @Cacheable(value = "recommendations", key = "#user_id")
    public RecommendationResponseDto getRecommendationsByUserId(UUID user_id) {
        List<RecommendationDto> recommendations = ruleRepository.findAll().stream()
                .filter(rule -> isValidRule(user_id, rule.getRule()))
                .map(this::toRecommendationDto)
                .collect(Collectors.toList());

        return new RecommendationResponseDto(user_id, recommendations);
    }

    private RecommendationDto toRecommendationDto(RuleEntity rule) {
        return new RecommendationDto(rule.getProductId(), rule.getProductName(), rule.getProductText());
    }

    /**
     * Метод проверяет одно правило по всем подправилам для заданного пользователя
     *
     * @param user_id  идентификатор пользователя
     * @param rulesDto массив подправил для рекомендации
     * @return boolean true-если пользователь удовлетворяет всем правилам рекомендации и false, если нет
     *
     */
    private boolean isValidRule(UUID user_id, List<OneRuleDto> rulesDto) {
        return rulesDto.stream()
                .allMatch(rule -> {
                    RuleCheck ruleCheck = ruleCheckMap.get(QueryType.valueOf(rule.query().toUpperCase()));
                    if (ruleCheck == null) {
                        throw new IllegalArgumentException("Нет объекта класса проверки для типа запроса: " + rule.query());
                    }
                    return ruleCheck.check(user_id, rule);
                });
    }

}

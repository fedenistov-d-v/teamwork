package com.starbank.recommendation.service;

import com.starbank.recommendation.model.*;
import com.starbank.recommendation.model.enums.QueryType;
import com.starbank.recommendation.repository.RuleRepository;
import com.starbank.recommendation.rule.RuleCheck;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Сервис рекомендаций
 * Инжектирует List<RuleCheck> - все объекты по проверке правил рекомендаций
 */
@Service
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

    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    public RecommendationService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    /**
     * Метод выдает рекомендации клиенту банка в зависимости от истории его операций в банке
     *
     * @param user_id - ID клиента банка.
     * @return массив рекомендаций для этого пользователя
     */
    @Cacheable(value = "recommendations", key = "#userId")
    public RecommendationResponseDto getRecommendationsByUserId(UUID user_id) {

        List<RecommendationDto> recommendations = new ArrayList<>();
        List<RuleEntity> rules = ruleRepository.findAll();
        for (RuleEntity rule : rules) {
            if (isValidRule(user_id, rule.getRule())) {
                recommendations.add(new RecommendationDto(rule.getProductId(), rule.getProductName(), rule.getProductText()));
            }
        }

        return new RecommendationResponseDto(user_id, recommendations);
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

        boolean result = true;
        for (OneRuleDto rule : rulesDto) {
            try {
                QueryType queryType = QueryType.valueOf(rule.query().toUpperCase());
                RuleCheck ruleCheck = ruleCheckMap.get(queryType);
                if (ruleCheck == null) {
                    throw new IllegalArgumentException("Нет объекта класса проверки для типа запроса: " + queryType);
                }
                result = result && ruleCheck.check(user_id, rule);
                if (!result) return false;
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

}

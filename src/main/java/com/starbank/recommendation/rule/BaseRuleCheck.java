package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.OneRuleDto;
import com.starbank.recommendation.model.enums.QueryType;
import com.starbank.recommendation.repository.H2Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Базовый класс для создания правил рекомендаций
 */
@Slf4j
public abstract class BaseRuleCheck implements RuleCheck {
    protected final QueryType queryType;

    @Autowired
    protected H2Repository h2Repository;

    public BaseRuleCheck(QueryType queryType) {
        this.queryType = queryType;
    }

    @Override
    public QueryType getQueryType() {
        return queryType;
    }

    @Override
    public boolean check(UUID user_id, OneRuleDto rule) {
        try {
            return isEligible(user_id, rule);
        } catch (IllegalArgumentException e) {
            log.error("Ошибка при обработке правила:user_id= " + user_id
                           + ", rule =" + rule.toString() + ". Текст ошибки:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected abstract boolean isEligible(UUID user_id, OneRuleDto rule);
}


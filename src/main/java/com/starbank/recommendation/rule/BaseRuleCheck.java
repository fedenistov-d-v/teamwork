package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.OneRuleDto;
import com.starbank.recommendation.model.enums.ProductType;
import com.starbank.recommendation.model.enums.QueryType;
import com.starbank.recommendation.repository.H2Repository;
import com.starbank.recommendation.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public abstract class BaseRuleCheck implements RuleCheck {
    protected QueryType queryType;

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
        return isEligible(user_id, rule);
    }

    protected abstract boolean isEligible(UUID user_id, OneRuleDto rule);
}



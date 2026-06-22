package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.OneRuleDto;
import com.starbank.recommendation.model.enums.QueryType;

import java.util.UUID;

public interface RuleCheck {

    public boolean check(UUID user_id, OneRuleDto rule);

   QueryType getQueryType();
}

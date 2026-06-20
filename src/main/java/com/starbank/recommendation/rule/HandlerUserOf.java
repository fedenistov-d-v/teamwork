package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.OneRuleDto;
import com.starbank.recommendation.model.enums.ProductType;
import com.starbank.recommendation.model.enums.QueryType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HandlerUserOf extends BaseRuleCheck {

    public HandlerUserOf() {
        super(QueryType.USER_OF);
    }

    @Override
    protected boolean isEligible(UUID user_id, OneRuleDto rule) {
        try {
            ProductType productType = ProductType.valueOf(rule.arguments().get(0).toUpperCase());
            return (rule.negate() ? !h2Repository.usesProductType(user_id, productType) : h2Repository.usesProductType(user_id, productType));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.OneRuleDto;
import com.starbank.recommendation.model.enums.ProductType;
import com.starbank.recommendation.model.enums.QueryType;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Проверяет использование клиентом банка какого-либо продукта банка
 */
@Component
public class HandlerUserOf extends BaseRuleCheck {

    public HandlerUserOf() {
        super(QueryType.USER_OF);
    }

    @Override
    protected boolean isEligible(UUID user_id, OneRuleDto rule) {
        ProductType productType = ProductType.valueOf(rule.arguments().get(0).toUpperCase());

        boolean result = h2Repository.usesProductType(user_id, productType);
        return (rule.negate() != result);
    }
}

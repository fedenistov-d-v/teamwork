package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.OneRuleDto;
import com.starbank.recommendation.model.enums.ProductType;
import com.starbank.recommendation.model.enums.QueryType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HandlerActiveUserOf extends BaseRuleCheck {

    // Количество транзакций для признания клиента активным
    private final int requireTransactionsCount = 5;

    public HandlerActiveUserOf() {
        super(QueryType.ACTIVE_USER_OF);
    }

    @Override
    protected boolean isEligible(UUID user_id, OneRuleDto rule) {
        try {
            ProductType productType = ProductType.valueOf(rule.arguments().get(0).toUpperCase());
            boolean result = h2Repository.countTransactionByProductType(user_id, productType) >= requireTransactionsCount;
            return (rule.negate() ? !result : result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}

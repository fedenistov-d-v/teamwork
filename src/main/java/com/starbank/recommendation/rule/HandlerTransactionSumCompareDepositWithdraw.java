package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.OneRuleDto;
import com.starbank.recommendation.model.enums.OperationType;
import com.starbank.recommendation.model.enums.ProductType;
import com.starbank.recommendation.model.enums.QueryType;
import com.starbank.recommendation.model.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Сравнивает сумму транзакций типа DEPOSIT и WITHDRAW по продукту банка
 */
@Component
public class HandlerTransactionSumCompareDepositWithdraw extends BaseRuleCheck {
    public HandlerTransactionSumCompareDepositWithdraw() {
        super(QueryType.TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW);
    }

    @Override
    protected boolean isEligible(UUID user_id, OneRuleDto rule) {
        ProductType productType = ProductType.valueOf(rule.arguments().get(0).toUpperCase());
        String operator = rule.arguments().get(1);

        int depositSum = (int) h2Repository.sumByTransactionTypeAndProductType(user_id, TransactionType.DEPOSIT, productType);
        int withdrawSum = (int) h2Repository.sumByTransactionTypeAndProductType(user_id, TransactionType.WITHDRAW, productType);
        OperationType op = OperationType.fromSymbol(operator);
        boolean result = op.check(depositSum, withdrawSum);
        return (rule.negate() != result);
    }
}

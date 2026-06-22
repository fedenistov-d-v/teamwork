package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.OneRuleDto;
import com.starbank.recommendation.model.enums.OperationType;
import com.starbank.recommendation.model.enums.ProductType;
import com.starbank.recommendation.model.enums.QueryType;
import com.starbank.recommendation.model.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Проверяет сумму транзакции по типам транзакций и продукту банка
 */
@Component
public class HandlerTransactionSumCompare extends BaseRuleCheck {

    public HandlerTransactionSumCompare() {
        super(QueryType.TRANSACTION_SUM_COMPARE);
    }

    @Override
    protected boolean isEligible(UUID user_id, OneRuleDto rule) {
        ProductType productType = ProductType.valueOf(rule.arguments().get(0).toUpperCase());
        TransactionType transactionType = TransactionType.valueOf(rule.arguments().get(1).toUpperCase());
        String operator = rule.arguments().get(2);
        int requireSum = Integer.parseInt(rule.arguments().get(3));

        int sumTransaction = (int) h2Repository.sumByTransactionTypeAndProductType(user_id, transactionType, productType);
        OperationType op = OperationType.fromSymbol(operator);
        boolean result = op.check(sumTransaction, requireSum);
        return (rule.negate() != result);
    }
}

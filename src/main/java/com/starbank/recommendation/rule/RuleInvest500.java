package com.starbank.recommendation.rule;

import com.starbank.recommendation.modul.enumOfTypes.ProductType;
import com.starbank.recommendation.modul.enumOfTypes.TransactionType;
import com.starbank.recommendation.repository.H2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Правила рекомендации "Invest 500"
 */
@Component
public class RuleInvest500 extends TemplateForRules {
    private static final Logger logger = LoggerFactory.getLogger(RuleInvest500.class);
    private static final long SAVING = 1000;
    private final H2Repository repository;

    public RuleInvest500(H2Repository repository) {
        super(
                UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                "Invest 500",
                "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! " +
                        "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. " +
                        "Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. " +
                        "Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. " +
                        "Откройте ИИС сегодня и станьте ближе к финансовой независимости!"
        );
        this.repository = repository;
    }

    /**
     * Метод проверки правил.
     *
     * @param userId - ID клиента банка.
     * @return Возвращает результат проверки false или true.
     */
    @Override
    protected boolean isEligible(UUID userId) {
        boolean hasDebit = repository.usesProductType(userId, ProductType.DEBIT);
        long sumSavingDeposit = repository.sumByTransactionTypeAndProductType(userId,
                TransactionType.DEPOSIT,
                ProductType.SAVING);
        boolean hasInvest = repository.usesProductType(userId, ProductType.INVEST);

        boolean verificationResult = hasDebit && !hasInvest && (sumSavingDeposit > SAVING);
        logger.debug("Результат проверки для рекомендации \"Invest 500\" = {}", verificationResult);
        return verificationResult;
    }

}
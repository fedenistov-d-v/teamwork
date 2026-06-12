package com.starbank.recommendation.rule;

import com.starbank.recommendation.modul.enumOfTypes.ProductType;
import com.starbank.recommendation.modul.enumOfTypes.TransactionType;
import com.starbank.recommendation.repository.H2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Правила рекомендации "Top Saving"
 */
@Component
public class RuleTopSaving extends TemplateForRules {
    private static final Logger logger = LoggerFactory.getLogger(RuleTopSaving.class);
    private static final long MIN_DEBIT_DEPOSIT = 50000;
    private static final long MIN_SAVING_DEPOSIT = 50000;
    private final H2Repository repository;

    public RuleTopSaving(H2Repository repository) {
        super(
                UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
                "Top Saving",
                "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. " +
                        "Больше никаких забытых чеков и потерянных квитанций — всё под контролем! " +
                        "Преимущества «Копилки»: Накопление средств на конкретные цели. " +
                        "Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет. " +
                        "Прозрачность и контроль. " +
                        "Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости. " +
                        "Безопасность и надежность. " +
                        "Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг. " +
                        "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!"
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
        long sumDebitDeposit = repository.sumByTransactionTypeAndProductType(userId,
                TransactionType.DEPOSIT,
                ProductType.DEBIT);
        long sumDebitSaving = repository.sumByTransactionTypeAndProductType(userId,
                TransactionType.DEPOSIT,
                ProductType.SAVING);
        long sumDebitWithdraw = repository.sumByTransactionTypeAndProductType(userId,
                TransactionType.WITHDRAW,
                ProductType.DEBIT);

        boolean verificationResult = hasDebit &&
                (sumDebitDeposit >= MIN_DEBIT_DEPOSIT || sumDebitSaving >= MIN_SAVING_DEPOSIT) &&
                (sumDebitDeposit > sumDebitWithdraw);
        logger.debug("Результат проверки для рекомендации \"Top Saving\" = {}", verificationResult);
        return verificationResult;
    }
}

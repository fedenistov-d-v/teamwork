package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.enumOfTypes.ProductType;
import com.starbank.recommendation.model.enumOfTypes.TransactionType;
import com.starbank.recommendation.repository.jdbc.H2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Правила рекомендации "Простой кредит"
 */
@Component
public class RuleSimpleLoan extends TemplateForRules {
    private static final Logger logger = LoggerFactory.getLogger(RuleSimpleLoan.class);
    private static final long MIN_DEBIT_WITHDRAW = 100000;

    @Qualifier("jdbcRuleRepository")
    private final H2Repository repository;

    public RuleSimpleLoan(H2Repository repository) {
        super(
                UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                "Простой кредит",
                "Откройте мир выгодных кредитов с нами!" +
                        "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! " +
                        "Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту." +
                        "Почему выбирают нас:" +
                        "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов." +
                        "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении." +
                        "Широкий выбор кредитных продуктов. " +
                        "Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое." +
                        "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"
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
        boolean hasCredit = repository.usesProductType(userId, ProductType.CREDIT);
        long sumDepositDebit = repository.sumByTransactionTypeAndProductType(userId,
                TransactionType.DEPOSIT,
                ProductType.DEBIT);
        long sumWithdrawDebit = repository.sumByTransactionTypeAndProductType(userId,
                TransactionType.WITHDRAW,
                ProductType.DEBIT);

        boolean verificationResult = !hasCredit &&
                (sumDepositDebit > sumWithdrawDebit) &&
                (sumWithdrawDebit > MIN_DEBIT_WITHDRAW);
        logger.debug("Результат проверки для рекомендации \"Простой кредит\" = {}", verificationResult);
        return verificationResult;
    }
}
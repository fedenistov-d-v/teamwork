package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.RecommendationDto;
import com.starbank.recommendation.repository.H2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Правила рекомендации "Простой кредит"
 */
@Component
public class RuleSimpleLoan implements RecommendationRuleSet {
    private static final Logger logger = LoggerFactory.getLogger(RuleSimpleLoan.class);
    private final RecommendationDto recommendation = new RecommendationDto(
            "Простой кредит",
            UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
            "Откройте мир выгодных кредитов с нами!\n" +
                    "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — " +
                    "именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и " +
                    "индивидуальный подход к каждому клиенту.\n" +
                    "Почему выбирают нас:\n" +
                    "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает " +
                    "всего несколько часов.\n" +
                    "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном " +
                    "приложении.\n" +
                    "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку " +
                    "недвижимости, автомобиля, образование, лечение и многое другое.\n" +
                    "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!");
    private final H2Repository repository;

    public RuleSimpleLoan(H2Repository repository) {
        this.repository = repository;
    }

    /**
     * Метод проверки правил.
     *
     * @param userId - ID клиента банка.
     * @return Возвращает объект рекомендации в виде Optional или null.
     */
    @Override
    public Optional<RecommendationDto> check(UUID userId) {
        boolean hasCredit = repository.usesProductType(userId, "CREDIT");
        long sumDepositDebit = repository.sumByTransactionTypeAndProductType(userId, "DEPOSIT", "DEBIT");
        long sumWithdrawDebit = repository.sumByTransactionTypeAndProductType(userId, "WITHDRAW", "DEBIT");

        if (!hasCredit && (sumDepositDebit > sumWithdrawDebit) && (sumWithdrawDebit > 100000)) {
            logger.debug("Прошла проверка для рекомендации \"Простой кредит\".");
            return Optional.of(recommendation);
        } else {
            logger.debug("Проверка НЕ прошла для рекомендации \"Простой кредит\".");
            return Optional.empty();
        }
    }
}

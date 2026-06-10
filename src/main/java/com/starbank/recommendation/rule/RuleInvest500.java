package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.RecommendationDto;
import com.starbank.recommendation.repository.H2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Правила рекомендации "Invest 500"
 */
@Component
public class RuleInvest500 implements RecommendationRuleSet {
    private static final Logger logger = LoggerFactory.getLogger(RuleInvest500.class);
    private final RecommendationDto recommendation = new RecommendationDto(
            "Invest 500",
            UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
            "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка!" +
                    "Воспользуйтесь налоговыми льготами и начните инвестировать с умом." +
                    "Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом" +
                    "периоде.Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными" +
                    "рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!");
    protected final H2Repository repository;

    public RuleInvest500(H2Repository repository) {
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
        boolean hasDebit = repository.usesProductType(userId, "DEBIT");
        boolean hssInvest = repository.usesProductType(userId, "INVEST");
        long sumDepositDebit = repository.sumByTransactionTypeAndProductType(userId, "DEPOSIT", "DEBIT");

        if (hasDebit && !hssInvest && sumDepositDebit > 1000) {
            logger.debug("Прошла проверка для рекомендации \"Invest 500\".");
            return Optional.of(recommendation);
        } else {
            logger.debug("Проверка НЕ прошла для рекомендации \"Invest 500\".");
            return Optional.empty();
        }
    }
}

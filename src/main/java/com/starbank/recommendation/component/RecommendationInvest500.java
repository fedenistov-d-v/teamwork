package com.starbank.recommendation.component;

import com.starbank.recommendation.model.RecommendationDto;
import com.starbank.recommendation.repository.GeneralQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Рекомендация банка "Invest 500"
 */
@Component
public class RecommendationInvest500 implements RecommendationRuleSet {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationInvest500.class);

    private final RecommendationDto recommendation = (new RecommendationDto(
            "Invest 500",
            UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
            "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! " +
                    "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца " +
                    "года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите " +
                    "возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными " +
                    "тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!"));

    /**
     *Метод проверки правил.
     * @param user_id - ID клиента банка.
     * @return Возвращает объект рекомендации в виде Optional.
     */
    @Override
    public Optional<RecommendationDto> check(UUID user_id) {
        if (GeneralQueries.hasDebit && !GeneralQueries.hasInvest && GeneralQueries.sumSavingDeposit > 1000) {
            logger.debug("Прошла проверка для рекомендации \"Invest 500\".");
            return Optional.of(recommendation);
        } else {
            logger.debug("Проверка НЕ прошла для рекомендации \"Invest 500\".");
            return Optional.empty();
        }
    }
}

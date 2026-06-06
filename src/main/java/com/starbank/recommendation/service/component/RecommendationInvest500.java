package com.starbank.recommendation.service.component;

import com.starbank.recommendation.modul.RecommendationDto;
import com.starbank.recommendation.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationInvest500 implements RecommendationRuleSet {
    private final RecommendationDto recommendation = (new RecommendationDto(
            "Invest 500",
            UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
            "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! " +
                    "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца " +
                    "года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите " +
                    "возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными " +
                    "тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!"));

    private boolean hasDebit = false;
    private boolean hasInvest = false;
    private int sumSaving = 0;

    private final RecommendationsRepository recommendationsRepository;

    public RecommendationInvest500(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationDto> check(UUID user_id) {
        if (hasDebit && !hasInvest && sumSaving > 1000) {
            return Optional.of(recommendation);
        } else return Optional.empty();
    }
}

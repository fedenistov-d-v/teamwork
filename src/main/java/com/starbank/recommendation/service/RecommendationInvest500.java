package com.starbank.recommendation.service;

import com.starbank.recommendation.model.RecommendationDto;
import com.starbank.recommendation.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationInvest500 extends BaseRecommendation implements RecommendationRuleSet {
    private static final RecommendationDto recommendation = (new RecommendationDto(
            "Invest 500",
            UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
            "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! " +
                    "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца " +
                    "года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите " +
                    "возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными " +
                    "тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!"));

    public RecommendationInvest500(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public Optional<RecommendationDto> check(UUID uid) {
        if (hasDebit(uid) && !hasInvest(uid) && sumSavingDeposit(uid) > 1000) {
            return Optional.of(recommendation);
        } else {
            return Optional.empty();
        }
    }
}

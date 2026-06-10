package com.starbank.recommendation.service;

import com.starbank.recommendation.model.RecommendationDto;
import com.starbank.recommendation.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationTopSaving extends BaseRecommendation implements RecommendationRuleSet {
    private final RecommendationDto recommendation = (new RecommendationDto(
            "Top Saving",
            UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
            "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский " +
                    "инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше " +
                    "никаких забытых чеков и потерянных квитанций — всё под контролем! " +
                    "Преимущества «Копилки»: " +
                    "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет " +
                    "автоматически переводить определенную сумму на ваш счет. " +
                    "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления " +
                    "и корректируйте стратегию при необходимости. " +
                    "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен " +
                    "только через мобильное приложение или интернет-банкинг. " +
                    "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!"));

    public RecommendationTopSaving(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public Optional<RecommendationDto> check(UUID uid) {
        Integer sumDebitDeposit = sumDebitDeposit(uid);
        Integer sumSavingDeposit = sumSavingDeposit(uid);

        if (hasDebit(uid) && ((sumDebitDeposit >= 50000) || (sumSavingDeposit >= 50000))
                && (sumDebitDeposit > sumDebitWithdraw(uid))) {
            return Optional.of(recommendation);
        } else {
            return Optional.empty();
        }
    }

}

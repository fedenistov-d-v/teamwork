package com.starbank.recommendation.service.component;

import com.starbank.recommendation.modul.RecommendationDto;
import com.starbank.recommendation.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationTopSaving implements RecommendationRuleSet {
    private final RecommendationDto recommendation = (new RecommendationDto(
            "Top Saving",
            UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
            "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский " +
                    "инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше " +
                    "никаких забытых чеков и потерянных квитанций — всё под контролем!\n" +
                    "Преимущества «Копилки»:\n" +
                    "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет " +
                    "автоматически переводить определенную сумму на ваш счет.\n" +
                    "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления " +
                    "и корректируйте стратегию при необходимости.\n" +
                    "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен " +
                    "только через мобильное приложение или интернет-банкинг.\n" +
                    "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!"));

    boolean hasDebit = false;
    int sumDebitDeposit = 0;
    int sumSavingDeposit = 0;
    int sumDebitWithdraw = 0;
    boolean balance = false;

    private final RecommendationsRepository recommendationsRepository;

    public RecommendationTopSaving(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationDto> check(UUID user_id) {
        hasDebit = recommendationsRepository.getHasDebit(user_id);
        sumDebitDeposit = recommendationsRepository.getSumDebitDeposit(user_id);
        sumSavingDeposit = recommendationsRepository.getSumSavingDeposit(user_id);
        sumDebitWithdraw = recommendationsRepository.getSumDebitWithdraw(user_id);
        balance = sumDebitDeposit > sumDebitWithdraw;
        if (hasDebit && (sumDebitDeposit >= 50000 || sumSavingDeposit >= 50000) && balance) {
            return Optional.of(recommendation);
        } else return Optional.empty();
    }
}

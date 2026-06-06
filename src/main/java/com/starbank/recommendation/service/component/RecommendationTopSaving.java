package com.starbank.recommendation.service.component;

import com.starbank.recommendation.modul.RecommendationDto;
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

    @Override
    public Optional<RecommendationDto> check(UUID user_id) {
        return Optional.of(recommendation);
    }
}

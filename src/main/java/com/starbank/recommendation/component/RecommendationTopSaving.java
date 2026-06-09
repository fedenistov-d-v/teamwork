package com.starbank.recommendation.component;

import com.starbank.recommendation.model.RecommendationDto;
import com.starbank.recommendation.repository.GeneralQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationTopSaving implements RecommendationRuleSet {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationInvest500.class);

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
        boolean balance = GeneralQueries.sumDebitDeposit > GeneralQueries.sumDebitWithdrawal;
        if (GeneralQueries.hasDebit && (GeneralQueries.sumDebitDeposit >= 50000 || GeneralQueries.sumSavingDeposit >= 50000) && balance) {
            logger.debug("Прошла проверка для рекомендации \"Top Saving\".");
            return Optional.of(recommendation);
        } else {
            logger.debug("Проверка НЕ прошла для рекомендации \"Top Saving\".");
            return Optional.empty();
        }
    }
}

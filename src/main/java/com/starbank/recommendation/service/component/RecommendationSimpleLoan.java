package com.starbank.recommendation.service.component;

import com.starbank.recommendation.modul.RecommendationDto;
import com.starbank.recommendation.repository.GeneralQueries;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationSimpleLoan implements RecommendationRuleSet {
    private final RecommendationDto recommendation = (new RecommendationDto(
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
                    "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"));

    @Override
    public Optional<RecommendationDto> check(UUID user_id) {
        if (!GeneralQueries.hasCredit && (GeneralQueries.sumDebitDeposit > GeneralQueries.sumDebitWithdrawal)
                && (GeneralQueries.sumDebitWithdrawal > 100000)) {
            return Optional.of(recommendation);
        } else return Optional.empty();
    }
}

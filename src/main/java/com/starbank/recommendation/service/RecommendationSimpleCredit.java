package com.starbank.recommendation.service;

import com.starbank.recommendation.model.RecommendationDto;
import com.starbank.recommendation.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationSimpleCredit extends BaseRecommendation implements RecommendationRuleSet {
    private final RecommendationDto recommendation = (new RecommendationDto(
            "Простой кредит",
            UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
            "Откройте мир выгодных кредитов с нами! " +
                    "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — " +
                    "именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и " +
                    "индивидуальный подход к каждому клиенту. " +
                    "Почему выбирают нас: " +
                    "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает " +
                    "всего несколько часов. " +
                    "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном " +
                    "приложении. " +
                    "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку " +
                    "недвижимости, автомобиля, образование, лечение и многое другое. " +
                    "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"));

    public RecommendationSimpleCredit(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public Optional<RecommendationDto> check(UUID uid) {
        Integer sumDeposit = sumDebitDeposit(uid);
        Integer sumWithdrawal = sumDebitWithdraw(uid);
        if (!hasCredit(uid) && (sumDeposit > sumWithdrawal) && sumWithdrawal > 100000) {
            return Optional.of(recommendation);
        } else {
            return Optional.empty();
        }
    }

}

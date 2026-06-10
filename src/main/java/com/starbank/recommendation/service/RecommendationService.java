package com.starbank.recommendation.service;

import com.starbank.recommendation.model.RecommendationDto;
import com.starbank.recommendation.repository.GeneralQueries;
import com.starbank.recommendation.repository.RecommendationsRepository;
import com.starbank.recommendation.component.RecommendationRuleSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис рекомендаций
 * Инжектирует List<RecommendationRuleSet> - список всех рекомендаций банка с условиями.
 * Инжектирует RecommendationsRepository generalRepository - репозиторий для подключения БД Н2.
 */
@Service
public class RecommendationService {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    private final List<RecommendationRuleSet> ruleSets;
    private final RecommendationsRepository generalRepository;

    public RecommendationService(List<RecommendationRuleSet> ruleSets, RecommendationsRepository generalRepository) {
        this.ruleSets = ruleSets;
        this.generalRepository = generalRepository;
    }

    /**
     * Метод определяет какие рекомендации рекомендовать клиенту банка.
     * Использует внутренний метод общих запросов в БД - calculateGeneralQueries().
     * @param user_id - ID клиента банка.
     * @return список рекомендаванных рекомендаций.
     */
    public List<RecommendationDto> getRecommendationsByIdUsers(UUID user_id) {
        logger.info("Вызван метод для получения рекомендаций с user_id = ({})", user_id);
        calculateGeneralQueries(user_id);
        logger.debug("hasDebit = ({}), hasInvest = ({}), hasCredit = ({}), " +
                        "sumDebitDeposit = ({}), sumSavingDeposit = ({}), sumDebitWithdrawal = ({}),",
                GeneralQueries.hasDebit,
                GeneralQueries.hasInvest,
                GeneralQueries.hasCredit,
                GeneralQueries.sumDebitDeposit,
                GeneralQueries.sumSavingDeposit,
                GeneralQueries.sumDebitWithdrawal);
        return ruleSets.stream()
                .map(rule -> rule.check(user_id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    /**
     * Метод общих запросов в БД
     * @param user_id - ID клиента банка.
     * Результаты общих запросов сохраняются в статических переменных класса GeneralQueries.
     */
    protected void calculateGeneralQueries(UUID user_id) {
        GeneralQueries.hasDebit = generalRepository.getHasDebit(user_id);
        GeneralQueries.hasInvest = generalRepository.getHasInvest(user_id);
        GeneralQueries.hasCredit = generalRepository.getHasCredit(user_id);
        GeneralQueries.sumDebitDeposit = generalRepository.getSumDebitDeposit(user_id);
        GeneralQueries.sumSavingDeposit = generalRepository.getSumSavingDeposit(user_id);
        GeneralQueries.sumDebitWithdrawal = generalRepository.getSumDebitWithdraw(user_id);
    }
}

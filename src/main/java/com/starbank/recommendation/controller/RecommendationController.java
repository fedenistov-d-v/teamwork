package com.starbank.recommendation.controller;

import com.starbank.recommendation.model.PatternJson;
import com.starbank.recommendation.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Контроллер рекомендаций
 * REST API
 * путь запросов - ./recommendation/
 * Инжектирует RecommendationService recommendationService - сервис рекомендаций с коммерческой логикой.
 */
@RestController
@RequestMapping("recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Метод запроса: GET /recommendation/<user_id>
     *
     * @param id - ID клиента банка
     * @return Формат JSON в виде:
     * {
     * "user_id": <user_id>,
     * "recommendations": [
     * {"name": <имя продукта>, "id": <id продукта>, "text": "текстовое описание продукта"},
     * ...
     * ]
     * }
     */
    @GetMapping("{id}")
    public PatternJson getRecommendations(@PathVariable UUID id) {
        return recommendationService.getRecommendationsByIdUsers(id);
    }
}

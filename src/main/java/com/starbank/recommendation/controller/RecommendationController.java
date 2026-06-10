package com.starbank.recommendation.controller;

import com.starbank.recommendation.model.PatternJson;
import com.starbank.recommendation.service.RecommendationService;
import org.springframework.http.ResponseEntity;
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
     * @param user_id - ID клиента банка
     * @return Формат JSON в виде:
     * {
     * 	"user_id": <user_id>,
     * 	"recommendations": [
     * 		        {"name": <имя продукта>, "id": <id продукта>, "text": "текстовое описание продукта"},
     * 		...
     * 	]
     * }
     */
    @GetMapping("{user_id}")
    public ResponseEntity<PatternJson> getRecommendations(@PathVariable UUID user_id) {
        return ResponseEntity.ok(new PatternJson(
                user_id,
                recommendationService.getRecommendationsByIdUsers(user_id)));
    }
}

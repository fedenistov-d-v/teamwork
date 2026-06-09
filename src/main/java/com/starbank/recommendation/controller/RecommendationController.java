package com.starbank.recommendation.controller;

import com.starbank.recommendation.model.PatternJson;
import com.starbank.recommendation.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("{user_id}")
    public ResponseEntity<PatternJson> getRecommendations(@PathVariable UUID user_id) {
        return ResponseEntity.ok(new PatternJson(
                user_id,
                recommendationService.getRecommendationsByIdUsers(user_id)));
    }
}

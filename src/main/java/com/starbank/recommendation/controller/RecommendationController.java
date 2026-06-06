package com.starbank.recommendation.controller;

import com.starbank.recommendation.modul.UserRecommendation;
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
    public ResponseEntity<UserRecommendation> getRecommendations(@PathVariable UUID user_id) {
        UserRecommendation recommendations = new UserRecommendation();
        recommendations.setUuid(user_id);
        recommendations.setRecommendations(recommendationService.getRecommendationsByIdUsers(user_id));
        return ResponseEntity.ok(recommendations);
    }
}

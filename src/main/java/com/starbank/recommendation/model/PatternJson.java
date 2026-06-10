package com.starbank.recommendation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Шаблон контроллера для правильного отображения формата JSON
 */
@Getter
@Setter
public class PatternJson {
    private UUID uuid;
    private List<RecommendationDto> recommendations;

    public PatternJson() {
    }

    public PatternJson(UUID uuid, List<RecommendationDto> recommendations) {
        this.uuid = uuid;
        this.recommendations = recommendations;
    }
}

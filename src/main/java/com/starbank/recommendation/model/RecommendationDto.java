package com.starbank.recommendation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Шаблон объектов рекомендаций для правильного отображения в JSON
 */
@Getter
@Setter
public class RecommendationDto {
    /**
     * Название рекомендации
     */
    private String name;
    /**
     * ID рекомендации
     */
    private UUID uuid;
    /**
     * Текст рекомендации
     */
    private String text;

    public RecommendationDto() {
    }

    public RecommendationDto(String name, UUID uuid, String text) {
        this.name = name;
        this.uuid = uuid;
        this.text = text;
    }
}

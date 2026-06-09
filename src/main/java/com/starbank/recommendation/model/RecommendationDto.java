package com.starbank.recommendation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecommendationDto {
    private String name;
    private UUID uuid;
    private String text;

    public RecommendationDto() {
    }

    public RecommendationDto(String name, UUID uuid, String text) {
        this.name = name;
        this.uuid = uuid;
        this.text = text;
    }
}

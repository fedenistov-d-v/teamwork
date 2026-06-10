package com.starbank.recommendation.model;


import java.util.UUID;

public record RecommendationDto(String name, UUID id, String text) {
}

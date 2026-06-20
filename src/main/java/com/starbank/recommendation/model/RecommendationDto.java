package com.starbank.recommendation.model;

import java.util.UUID;

public record RecommendationDto(
        UUID id,
        String name,
        String text
) {
}

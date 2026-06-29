package com.starbank.recommendation.model;

import java.util.UUID;

public record RecommendationDto(
        UUID user_id,
        String name,
        String text
) {
}

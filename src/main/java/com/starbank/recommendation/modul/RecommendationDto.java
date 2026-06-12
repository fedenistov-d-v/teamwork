package com.starbank.recommendation.modul;

import java.util.UUID;

public record RecommendationDto(
        UUID id,
        String name,
        String text
) {
}

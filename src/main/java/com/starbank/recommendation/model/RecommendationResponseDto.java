package com.starbank.recommendation.model;

import java.util.List;
import java.util.UUID;

public record RecommendationResponseDto(
        UUID user_id,
        List<RecommendationDto> recommendations)
{
}

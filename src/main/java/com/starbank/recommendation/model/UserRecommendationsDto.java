package com.starbank.recommendation.model;

import java.util.List;
import java.util.UUID;

public record UserRecommendationsDto(UUID user_id, List<RecommendationDto> recommendations) {
}

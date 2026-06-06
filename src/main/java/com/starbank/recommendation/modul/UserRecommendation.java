package com.starbank.recommendation.modul;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserRecommendation {
    private UUID uuid;
    private List<RecommendationDto> recommendations;
}

package com.starbank.recommendation.controller;

import com.starbank.recommendation.exception.NotFoundException;
import com.starbank.recommendation.exception.ValidationException;
import com.starbank.recommendation.service.RecommendationService;
import com.starbank.recommendation.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("recommendation")
@Tag(name = "Контроллер рекомендаций", description = "Контроллер по UUID пользователя выдает рекомендации по продуктам Банка")
public class RecommendationController {

    private final UserService userService;
    private final RecommendationService recommendationService;


    public RecommendationController(UserService userService, RecommendationService recommendationService) {
        this.userService = userService;
        this.recommendationService = recommendationService;
    }

    @GetMapping("{user_id}")
    public ResponseEntity<?> getRecommendations(@PathVariable String user_id) {

        user_id = user_id.trim();
        if (!isValidUUID(user_id)) {
            throw new ValidationException("Недопустимый идентификатор пользователя user_id");
        }

        UUID uid = UUID.fromString(user_id);
        if (!userService.isExistsUser(uid)) {
            throw new NotFoundException("Пользователя с таким идентификатором нет");
        }

        try {
            return ResponseEntity.ok(recommendationService.getUserRecommendationsByUserId(uid));
        } catch (Exception e) {
            log.error("Ошибка получения рекомендаций: user_id = " + user_id + ", " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    private static boolean isValidUUID(String uuid) {
        if (uuid == null) return false;
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}

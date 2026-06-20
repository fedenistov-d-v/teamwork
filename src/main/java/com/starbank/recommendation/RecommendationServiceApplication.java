package com.starbank.recommendation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @version Версия от 11.06.2026
 * Сервис рекомендаций для клиентов банка "Стар"
 */
@SpringBootApplication
@OpenAPIDefinition
@EnableCaching
public class RecommendationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecommendationServiceApplication.class, args);
    }
}

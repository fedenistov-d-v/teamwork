package com.starbank.recommendation.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Recommendation Api",
                description = "API системы рекоммендаций Старбанка",
                version = "1.0.0",
                contact = @Contact(
                        name = "Team Java",
                        email = "team@team.dev",
                        url = "https://team.dev"
                )
        )
)
public class OpenApiConfig {
}

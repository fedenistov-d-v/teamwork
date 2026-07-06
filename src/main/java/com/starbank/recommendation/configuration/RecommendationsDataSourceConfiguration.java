package com.starbank.recommendation.configuration;

import com.zaxxer.hikari.HikariDataSource;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Класс - конфигурация.
 */
@Configuration
@RequiredArgsConstructor
public class RecommendationsDataSourceConfiguration {

    private final DataSourceProperties properties;

    @Primary
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * Бин с названием "recommendationsDataSource"
     * Конфигурирование БД. Прописывается путь, драйвер.
     *
     * @param recommendationsUrl - путь к БД берётся из application.properties.
     * @return объект типа HikariDataSource.
     */
    @Bean(name = "recommendationsDataSource")
    public DataSource recommendationsDataSource(@Value("${application.recommendations-db.url}") String recommendationsUrl) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationsUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        return dataSource;
    }

    /**
     * Бин с названием "recommendationsJdbcTemplate"
     * Для использования методами в репозитории
     *
     * @param dataSource - объект тапа HikariDataSource берётся из бина "recommendationsDataSource"
     * @return Возвращается объект типа JdbcTemplate
     */
    @Bean(name = "recommendationsJdbcTemplate")
    public JdbcTemplate recommendationsJdbcTemplate(
            @Qualifier("recommendationsDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }
}

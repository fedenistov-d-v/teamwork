package com.starbank.recommendation.repository;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean getRandomTransactionAmount(UUID user){
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? true : false;
    }

    public boolean getHasDebit(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT t.AMOUNT  \n" +
                        "FROM TRANSACTIONS t \n" +
                        "WHERE t.USER_ID = '958c44cd-f82c-4d7d-97c5-eb1dc45ce6b5'\n" +
                        "AND EXISTS(\n" +
                        "SELECT 1\n" +
                        "FROM PRODUCTS p \n" +
                        "WHERE p.ID = t.PRODUCT_ID \n" +
                        "AND p.\"TYPE\" = 'DEBIT'\n" +
                        ")\n" +
                        "LIMIT 1",
                Integer.class,
                user_id);
        return result != null ? true : false;
    }
}
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

    public boolean getHasDebit(UUID user_id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT EXISTS( " +
                        "SELECT 1 " +
                        "FROM TRANSACTIONS t " +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.\"TYPE\" = 'DEBIT')",
                Boolean.class,
                user_id));
    }

    public boolean getHasInvest(UUID user_id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT EXISTS( " +
                        "SELECT 1 " +
                        "FROM TRANSACTIONS t " +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.\"TYPE\" = 'INVEST')",
                Boolean.class,
                user_id));
    }

    public boolean getHasCredit(UUID user_id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT EXISTS( " +
                        "SELECT 1 " +
                        "FROM TRANSACTIONS t " +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.\"TYPE\" = 'CREDIT')",
                Boolean.class,
                user_id));
    }

    public int getSumSavingDeposit(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT sum(t.AMOUNT ) " +
                        "FROM TRANSACTIONS t " +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.\"TYPE\" = 'SAVING' " +
                        "AND t.\"TYPE\" = 'DEPOSIT'",
                Integer.class,
                user_id);
        return result != null ? result : 0;
    }

    public int getSumDebitDeposit(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT sum(t.AMOUNT ) " +
                        "FROM TRANSACTIONS t " +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.\"TYPE\" = 'DEBIT' " +
                        "AND t.\"TYPE\" = 'DEPOSIT'",
                Integer.class,
                user_id);
        return result != null ? result : 0;
    }

    public int getSumDebitWithdraw(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT sum(t.AMOUNT ) " +
                        "FROM TRANSACTIONS t " +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.\"TYPE\" = 'DEBIT' " +
                        "AND t.\"TYPE\" = 'WITHDRAW'",
                Integer.class,
                user_id);
        return result != null ? result : 0;
    }
}
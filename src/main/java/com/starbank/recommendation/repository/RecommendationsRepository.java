package com.starbank.recommendation.repository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {
    protected boolean showSqlQueries = false;
    private static final Logger logger = LoggerFactory.getLogger(RecommendationsRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate,
                                     @Value("${show.sql.queries}") boolean showSqlQueries) {
        this.jdbcTemplate = jdbcTemplate;
        this.showSqlQueries = showSqlQueries;
    }

    public boolean getHasDebit(UUID user_id) {
        if (showSqlQueries)
            logger.info("SQL запрос: " +
                    "SELECT EXISTS( SELECT 1 FROM TRANSACTIONS t JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                    "WHERE t.USER_ID = {} AND p.\"TYPE\" = 'DEBIT')", user_id);
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
        if (showSqlQueries)
            logger.info("SQL запрос: " +
                    "SELECT EXISTS( SELECT 1 FROM TRANSACTIONS t JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                    "WHERE t.USER_ID = {} AND p.\"TYPE\" = 'INVEST')", user_id);
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
        if (showSqlQueries)
            logger.info("SQL запрос: " +
                    "SELECT EXISTS( SELECT 1 FROM TRANSACTIONS t JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                    "WHERE t.USER_ID = {} AND p.\"TYPE\" = 'CREDIT')", user_id);
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
        if (showSqlQueries)
            logger.info("SQL запрос: " +
                    "SELECT sum(t.AMOUNT ) FROM TRANSACTIONS t JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                    "WHERE t.USER_ID = {} AND p.\"TYPE\" = 'SAVING' ) AND t.\"TYPE\" = 'DEPOSIT'", user_id);
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
        if (showSqlQueries)
            logger.info("SQL запрос: " +
                    "SELECT sum(t.AMOUNT ) FROM TRANSACTIONS t JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                    "WHERE t.USER_ID = {} AND p.\"TYPE\" = 'DEBIT' ) AND t.\"TYPE\" = 'DEPOSIT'", user_id);
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
        if (showSqlQueries)
            logger.info("SQL запрос: " +
                    "SELECT sum(t.AMOUNT ) FROM TRANSACTIONS t JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                    "WHERE t.USER_ID = {} AND p.\"TYPE\" = 'DEBIT' ) AND t.\"TYPE\" = 'WITHDRAW'", user_id);
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
package com.starbank.recommendation.repository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий. Получения данных с базы данных Н2
 * Инжектирует JdbcTemplate jdbcTemplate - используется для создания SQL запросов.
 * Параметр boolean showSqlQueries - выводить в лог SQL или нет. Значение параметра берётся из application.properties
 */
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

    /**
     * Метод проверяет существование хотя бы одно записи в БД банка по продуктам банка с типом DEBIT
     * @param user_id - ID клиента банка.
     * @return Выводится булевское значение.
     */
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

    /**
     * Метод проверяет существование хотя бы одно записи в БД банка по продуктам банка с типом INVEST
     * @param user_id - ID клиента банка.
     * @return Выводится булевское значение.
     */
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

    /**
     * Метод проверяет существование хотя бы одно записи в БД банка по продуктам банка с типом CREDIT
     * @param user_id - ID клиента банка.
     * @return Выводится булевское значение.
     */
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

    /**
     * Метод выводит сумму пополнений по всем по всем операциям продуктов банка с типом SAVING
     * @param user_id - ID клиента банка.
     * @return Выводится булевское значение.
     */
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

    /**
     * Метод выводит сумму пополнений по всем по всем операциям продуктов банка с типом DEBIT
     * @param user_id - ID клиента банка.
     * @return Выводится булевское значение.
     */
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

    /**
     * Метод выводит сумму трат по всем по всем операциям продуктов банка с типом DEBIT.
     * @param user_id - ID клиента банка.
     * @return Выводится булевское значение.
     */
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
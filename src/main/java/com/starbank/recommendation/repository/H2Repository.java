package com.starbank.recommendation.repository;

import com.starbank.recommendation.modul.enumOfTypes.ProductType;
import com.starbank.recommendation.modul.enumOfTypes.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий. Получения данных с базы данных Н2
 * Инжектирует <code>JdbcTemplate jdbcTemplate</code> - используется для создания SQL запросов.
 * Параметр boolean showSqlQueries - выводить в лог SQL или нет. Значение параметра берётся из application.properties
 */
@Repository
public class H2Repository {
    protected boolean showSqlQueries;
    private static final Logger logger = LoggerFactory.getLogger(H2Repository.class);

    private final JdbcTemplate jdbcTemplate;

    public H2Repository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate,
                        @Value("${show.sql.queries}") boolean showSqlQueries) {
        this.jdbcTemplate = jdbcTemplate;
        this.showSqlQueries = showSqlQueries;
    }

    /**
     * Метод проверяет существование хотя бы одно записи в БД банка по типу продукта банка
     *
     * @param userId - ID клиента банка.
     * @return Выводится булевское значение.
     */
    public boolean usesProductType(UUID userId, ProductType productType) {
        if (showSqlQueries)
            logger.info("SQL запрос: " +
                    "SELECT EXISTS( SELECT 1 FROM TRANSACTIONS t JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                    "WHERE t.USER_ID = '{}' AND p.\"TYPE\" = '{}')", userId, productType);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT EXISTS( " +
                        "SELECT 1 " +
                        "FROM TRANSACTIONS t " +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.\"TYPE\" = ?)",
                Boolean.class,
                userId,
                productType.name()));
    }

    /**
     * Метод выводит сумму по типу транзакций и по типу продукта банка
     *
     * @param userId - ID клиента банка.
     */
    public long sumByTransactionTypeAndProductType(UUID userId,
                                                   TransactionType transactionType,
                                                   ProductType productType) {
        if (showSqlQueries)
            logger.info("SQL запрос: " +
                            "SELECT sum(t.AMOUNT ) FROM TRANSACTIONS t JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                            "WHERE t.USER_ID = '{}' AND p.\"TYPE\" = '{}' ) AND t.\"TYPE\" = '{}'",
                    userId, productType, transactionType);
        Long result = jdbcTemplate.queryForObject(
                "SELECT sum(t.AMOUNT ) " +
                        "FROM TRANSACTIONS t " +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.\"TYPE\" = ? " +
                        "AND t.\"TYPE\" = ?",
                Long.class,
                userId,
                productType.name(),
                transactionType.name());
        return result != null ? result : 0;
    }
}
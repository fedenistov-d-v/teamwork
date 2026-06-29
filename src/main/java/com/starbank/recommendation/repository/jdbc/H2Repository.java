package com.starbank.recommendation.repository.jdbc;

import com.starbank.recommendation.model.enums.ProductType;
import com.starbank.recommendation.model.enums.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;

import java.util.UUID;

/**
 * Репозиторий. Получения данных с базы данных Н2
 * Инжектирует <code>JdbcTemplate jdbcTemplate</code> - используется для создания SQL запросов.
 * Параметр boolean showSqlQueries - выводить в лог SQL или нет. Значение параметра берётся из application.properties
 */
@Repository("jdbcRuleRepository")
@EnableJdbcRepositories(basePackages = "com.starbank.recommendation.repository.jdbc")
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
     * @param userId      - ID клиента банка.
     * @param productType - тип продукта банка
     * @return Выводится булевское значение.
     */

    @Cacheable(
            value = "transaction-count-cache",
            key = "#userId + '_' + #productType"
    )

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
     * @param userId          - ID клиента банка.
     * @param transactionType - тип транзакции
     * @param productType     - тип продукта банка
     */
    @Cacheable(
            value = "transaction-sum-cache",
            key = "#userId + '_' + #transactionType + '_' + #productType"
    )

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

    /**
     * Метод выводит количество транзакций клиента банка по типу продукта банка
     *
     * @param userId          - ID клиента банка.
     * @param productType     - тип продукта банка
     */

    @Cacheable(
            value = "transaction-count-cache",
            key = "#userId + '_' + #productType + '_' + #count"
    )

    public long countTransactionByProductType(UUID userId, ProductType productType) {

        if (showSqlQueries)
            logger.info("SQL запрос: " +
                            "SELECT count(t.TYPE) FROM TRANSACTIONS t JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                            "WHERE t.USER_ID = '{}' AND p.\"TYPE\" = '{}' )",
                    userId, productType);
        Long result = jdbcTemplate.queryForObject(
                "SELECT count(t.TYPE ) " +
                        "FROM TRANSACTIONS t " +
                        "JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.\"TYPE\" = ? ",
                Long.class,
                userId,
                productType.name());

        return result;
    }

}
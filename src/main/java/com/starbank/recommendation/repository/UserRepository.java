package com.starbank.recommendation.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isExistsUser(UUID uid) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE id = ?";
        Integer result = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                uid);
        return (result != null) && (result == 1);

    }

    public boolean hasProduct(UUID uid, String productName) {
        String sql = "SELECT COUNT(*) from TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE=?";
        Integer result = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                uid, productName);
        return (result != null) && (result >= 1);
    }

    public Integer getDepositAmountProduct(UUID uid, String productName) {
        String sql = "SELECT SUM(AMOUNT) AS Result FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID AND t.TYPE = 'DEPOSIT' " +
                "WHERE t.USER_ID = ? AND p.TYPE=?";
        Integer result = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                uid, productName);
        return (result != null) ? result : 0;
    }

    public Integer getWithdrawAmountProduct(UUID uid, String productName) {
        String sql = "SELECT SUM(AMOUNT) AS Result FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID AND t.TYPE = 'WITHDRAW' " +
                "WHERE t.USER_ID = ? AND p.TYPE=?";
        Integer result = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                uid, productName);
        return (result != null) ? result : 0;
    }
}

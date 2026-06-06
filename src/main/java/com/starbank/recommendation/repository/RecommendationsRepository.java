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
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT t.AMOUNT  \n" +
                            "FROM TRANSACTIONS t \n" +
                            "WHERE t.USER_ID = ? \n" +
                            "AND EXISTS(\n" +
                            "SELECT 1\n" +
                            "FROM PRODUCTS p \n" +
                            "WHERE p.ID = t.PRODUCT_ID \n" +
                            "AND p.\"TYPE\" = 'DEBIT'\n" +
                            ")\n" +
                            "LIMIT 1",
                    Integer.class,
                    user_id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean getHasInvest(UUID user_id) {
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT t.AMOUNT  \n" +
                            "FROM TRANSACTIONS t \n" +
                            "WHERE t.USER_ID = ? \n" +
                            "AND EXISTS(\n" +
                            "SELECT 1\n" +
                            "FROM PRODUCTS p \n" +
                            "WHERE p.ID = t.PRODUCT_ID \n" +
                            "AND p.\"TYPE\" = 'INVEST'\n" +
                            ")\n" +
                            "LIMIT 1",
                    Integer.class,
                    user_id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean getHasCredit(UUID user_id) {
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT t.AMOUNT  \n" +
                            "FROM TRANSACTIONS t \n" +
                            "WHERE t.USER_ID = ? \n" +
                            "AND EXISTS(\n" +
                            "SELECT 1\n" +
                            "FROM PRODUCTS p \n" +
                            "WHERE p.ID = t.PRODUCT_ID \n" +
                            "AND p.\"TYPE\" = 'CREDIT'\n" +
                            ")\n" +
                            "LIMIT 1",
                    Integer.class,
                    user_id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

        public int getSumSavingDeposit (UUID user_id){
            Integer result;
            try {
                result = jdbcTemplate.queryForObject(
                        "SELECT sum(t.AMOUNT) \n" +
                                "FROM TRANSACTIONS t \n" +
                                "WHERE t.USER_ID = ?\n" +
                                "AND t.\"TYPE\" = 'DEPOSIT'\n" +
                                "AND (\n" +
                                "\tSELECT 1 \n" +
                                "\tFROM PRODUCTS p \n" +
                                "\tWHERE p.ID = t.PRODUCT_ID \n" +
                                "\tAND p.\"TYPE\" = 'SAVING'\n" +
                                ")",
                        Integer.class,
                        user_id);
            } catch (Exception e) {
                return 0;
            }
            return result != null ? result : 0;
        }

        public int getSumDebitDeposit (UUID user_id){
            Integer result;
            try {
                result = jdbcTemplate.queryForObject(
                        "SELECT sum(t.AMOUNT) \n" +
                                "FROM TRANSACTIONS t \n" +
                                "WHERE t.USER_ID = ?\n" +
                                "AND t.\"TYPE\" = 'DEPOSIT'\n" +
                                "AND (\n" +
                                "\tSELECT 1 \n" +
                                "\tFROM PRODUCTS p \n" +
                                "\tWHERE p.ID = t.PRODUCT_ID \n" +
                                "\tAND p.\"TYPE\" = 'DEBIT'\n" +
                                ")",
                        Integer.class,
                        user_id);
            } catch (Exception e) {
                return 0;
            }
            return result != null ? result : 0;
        }

        public int getSumDebitWithdraw (UUID user_id){
            Integer result;
            try {
                result = jdbcTemplate.queryForObject(
                        "SELECT sum(t.AMOUNT) \n" +
                                "FROM TRANSACTIONS t \n" +
                                "WHERE t.USER_ID = ?\n" +
                                "AND t.\"TYPE\" = 'WITHDRAW'\n" +
                                "AND (\n" +
                                "\tSELECT 1 \n" +
                                "\tFROM PRODUCTS p \n" +
                                "\tWHERE p.ID = t.PRODUCT_ID \n" +
                                "\tAND p.\"TYPE\" = 'DEBIT'\n" +
                                ")",
                        Integer.class,
                        user_id);
            } catch (Exception e) {
                return 0;
            }
            return result != null ? result : 0;
        }
    }
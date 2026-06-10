package com.starbank.recommendation.repository;

/**
 * Класс статических переменных для хранения результатов общих запросов в БД.
 */
public class GeneralQueries {
    /**
     * Пользователь использует продукт с типом DEBIT.
     */
    public static boolean hasDebit = false;
    /**
     * Пользователь использует продукты с типом INVEST.
     */
    public static boolean hasInvest = false;
    /**
     * Пользователь использует продукты с типом CREDIT.
     */
    public static boolean hasCredit = false;
    /**
     * Сумма пополнений по всем продуктам с типом SAVING.
     */
    public static int sumSavingDeposit = 0;
    /**
     * Сумма пополнений по всем продуктам с типом DEBIT.
     */
    public static int sumDebitDeposit = 0;
    /**
     * Сумма трат по всем продуктам с типом DEBIT.
     */
    public static int sumDebitWithdrawal = 0;
}

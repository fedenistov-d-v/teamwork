package com.starbank.recommendation.service;

import com.starbank.recommendation.repository.UserRepository;

import java.util.UUID;

public class BaseRecommendation {

    private final UserRepository userRepository;

    public BaseRecommendation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected Integer sumDebitDeposit(UUID uuid) {
        return userRepository.getDepositAmountProduct(uuid, "DEBIT");
    }

    protected Integer sumDebitWithdraw(UUID uuid) {
        return userRepository.getWithdrawAmountProduct(uuid, "DEBIT");
    }

    protected Integer sumSavingDeposit(UUID uuid) {
        return userRepository.getDepositAmountProduct(uuid, "SAVING");
    }

    protected boolean hasDebit(UUID uid) {
        return userRepository.hasProduct(uid, "DEBIT");
    }

    protected boolean hasCredit(UUID uid) {
        return userRepository.hasProduct(uid, "CREDIT");
    }

    protected boolean hasInvest(UUID uid) {
        return userRepository.hasProduct(uid, "INVEST");
    }

    protected boolean hasSaving(UUID uid) {
        return userRepository.hasProduct(uid, "SAVING");
    }

}

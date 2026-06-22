package com.starbank.recommendation.model.enums;

import lombok.Getter;

@Getter
public enum OperationType {
    GT(">"),
    LT("<"),
    EQ("="),
    GTE(">="),
    LTE("<=");

    private final String symbol;

    OperationType(String symbol) {
        this.symbol = symbol;
    }

    public boolean check(int sum, int c) {
        return switch (this) {
            case GT -> sum > c;
            case LT -> sum < c;
            case EQ -> sum == c;
            case GTE -> sum >= c;
            case LTE -> sum <= c;
        };
    }

    // Преобразует строку в enum
    public static OperationType fromSymbol(String symbol) {
        for (OperationType op : values()) {
            if (op.symbol.equals(symbol)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Неизвестный оператор: " + symbol);
    }
}

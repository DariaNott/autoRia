package com.ria.auto.entity;

public enum Currency {

    USD("$"),
    EUR("€"),
    UAH("грн.");

    private String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    public static Currency getCurrency(String symbol) {
        for (Currency cur : values()) {
            if (symbol.equals(cur.getSymbol())) {
                return cur;
            }
        }
        throw new RuntimeException("Cannot define currency for string: " + symbol);
    }

    public String getSymbol() {
        return symbol;
    }
}

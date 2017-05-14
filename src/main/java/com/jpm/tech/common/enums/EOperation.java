package com.jpm.tech.common.enums;

public enum EOperation {
    BUY("B"), SELL("S");

    private String shortName;

    EOperation(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return shortName;
    }
}

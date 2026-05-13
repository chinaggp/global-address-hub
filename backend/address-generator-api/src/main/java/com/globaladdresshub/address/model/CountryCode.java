package com.globaladdresshub.address.model;

import java.util.Arrays;
import java.util.Optional;

public enum CountryCode {
    US("United States"),
    JP("Japan"),
    UK("United Kingdom"),
    CA("Canada"),
    AU("Australia"),
    TR("Turkey"),
    NG("Nigeria");

    private final String displayName;

    CountryCode(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    public static Optional<CountryCode> from(String value) {
        if (value == null || value.isBlank()) {
            return Optional.empty();
        }
        String normalized = value.trim().toUpperCase();
        return Arrays.stream(values()).filter(code -> code.name().equals(normalized)).findFirst();
    }
}

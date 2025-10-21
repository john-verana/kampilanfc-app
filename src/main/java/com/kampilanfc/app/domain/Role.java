package com.kampilanfc.app.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ADMIN, COACH, MANAGER, PLAYER;

    @JsonCreator
    public static Role from(String value) {
        return value == null ? null : Role.valueOf(value.trim().toUpperCase());
    }
}

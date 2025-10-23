package com.kampilanfc.app.dto.response;

public class UsernameCheckResponse {

    private final boolean available;
    private final String message;

    // constructor
    public UsernameCheckResponse(boolean available, String message) {
        this.available = available;
        this.message = message;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getMessage() {
        return message;
    }
}
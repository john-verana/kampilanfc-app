package com.kampilanfc.app.validator;


public class UsernameAvailabilityResult {

    private final boolean available;
    private final String message;
    private final UsernameCheckStatus status;

    public enum UsernameCheckStatus {
        AVAILABLE,
        TAKEN,
        INVALID_FORMAT
    }

    private UsernameAvailabilityResult(boolean available, String message, UsernameCheckStatus status) {
        this.available = available;
        this.message = message;
        this.status = status;
    }

    // Factory methods
    public static UsernameAvailabilityResult available() {
        return new UsernameAvailabilityResult(true, "Username is available",
                UsernameCheckStatus.AVAILABLE
                );
    }

    public static UsernameAvailabilityResult taken() {
        return new UsernameAvailabilityResult(false, "Username is already taken",
                UsernameCheckStatus.TAKEN
                );
    }

    public static UsernameAvailabilityResult invalidFormat(String reason) {
        return new UsernameAvailabilityResult(false, reason,
                UsernameCheckStatus.INVALID_FORMAT
                );
    }

    // getters
    public boolean isAvailable() {
        return available;
    }
    public String getMessage() {
        return message;
    }
    public UsernameCheckStatus getStatus() {
        return status;
    }
}

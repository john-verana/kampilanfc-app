package com.kampilanfc.app.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UsernameValidator {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;
    private static final String USERNAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9._]+$";

    private static final Pattern PATTERN = Pattern.compile(USERNAME_PATTERN);

    public ValidationResult validate(String username) {
        if (username == null || username.isBlank())
            return ValidationResult.failure("Username is required");

        if (username.length() < MIN_LENGTH) {
            return ValidationResult.failure("Username too short (minimum 3 characters)");
        }

        if (username.length() > MAX_LENGTH) {
            return ValidationResult.failure("Username too long (maximum 20 characters)");
        }

        if (!PATTERN.matcher(username).matches()) {
            return ValidationResult.failure("Username can only contain letters, numbers, dots, and underscores");
        }
        return ValidationResult.success();
    }
}
package com.kampilanfc.app.service;

import com.kampilanfc.app.repository.UserRepository;
import com.kampilanfc.app.validator.UsernameAvailabilityResult;
import com.kampilanfc.app.validator.UsernameValidator;
import com.kampilanfc.app.validator.ValidationResult;
import org.springframework.stereotype.Service;

@Service
public class UsernameAvailabilityService {

    private final UsernameValidator usernameValidator;
    private final UserRepository userRepository;

    public UsernameAvailabilityService(UsernameValidator usernameValidator,
                                        UserRepository userRepository) {
        this.usernameValidator = usernameValidator;
        this.userRepository = userRepository;
    }

    public UsernameAvailabilityResult checkAvailability(String username) {

        ValidationResult validationResult = usernameValidator.validate(username);

        if (!validationResult.isValid()) {
            return UsernameAvailabilityResult.invalidFormat(validationResult.getErrorMessage());
        }

        String normalizedUsername = username.toLowerCase();
        boolean exists = userRepository.existsByUsername(normalizedUsername);

        if (exists) {
            return UsernameAvailabilityResult.taken();
        }

        return UsernameAvailabilityResult.available();
    }
}

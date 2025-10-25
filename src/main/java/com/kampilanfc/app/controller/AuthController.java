package com.kampilanfc.app.controller;


import com.kampilanfc.app.dto.request.UsernameCheckRequest;
import com.kampilanfc.app.dto.response.UsernameCheckResponse;
import com.kampilanfc.app.service.UsernameAvailabilityService;
import com.kampilanfc.app.validator.UsernameAvailabilityResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsernameAvailabilityService usernameAvailabilityService;

    public AuthController(UsernameAvailabilityService usernameAvailabilityService) {
        this.usernameAvailabilityService = usernameAvailabilityService;
    }

    @PostMapping("/check-username")
    public ResponseEntity<UsernameCheckResponse> checkUsername(
            @Valid @RequestBody UsernameCheckRequest request) {

        UsernameAvailabilityResult result = usernameAvailabilityService
                .checkAvailability(request.getUsername());

        UsernameCheckResponse response = new UsernameCheckResponse(
                result.isAvailable(),
                result.getMessage()
        );

        switch (result.getStatus()) {
            case AVAILABLE:
                return ResponseEntity.ok(response);
            case TAKEN:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            case INVALID_FORMAT:
                return ResponseEntity.badRequest().body(response);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

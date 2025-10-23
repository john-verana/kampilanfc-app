package com.kampilanfc.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsernameCheckRequest {

    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username cannot exceed 20 characters")
    private String username;

    // getter
    public String getUsername() {
        return username;
    }

    // setter
    public void setUsername(String username) {
        this.username = username;
    }
}

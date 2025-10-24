package com.kampilanfc.app.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UsernameCheckRequest {

    @NotBlank(message = "Username is required")
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

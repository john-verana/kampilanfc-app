package com.kampilanfc.app.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.time.Instant;


@Entity
@Table(name = "users",
uniqueConstraints = {
    @UniqueConstraint(name = "uk_users_username", columnNames = "username"),
    @UniqueConstraint(name = "uk_users_email", columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9._]{3,20}$", message = "User name must be between 3 and 20 characters long and contain only letters, numbers, . and _")
    @Column(nullable = false, length = 20)
    private String username;

    @Email(message = "Invalid email")
    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    private boolean verified = false;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    private Instant lastLoginAt;

    @PrePersist
    private void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = Instant.now();
    }


    //getters & setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Instant getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(Instant lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    }





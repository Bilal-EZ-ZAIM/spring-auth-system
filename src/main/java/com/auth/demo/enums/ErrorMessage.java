package com.auth.demo.enums;

public enum ErrorMessage {

    INVALID_CREDENTIALS("Invalid credentials"),
    EMAIL_ALREADY_USED("Email already in use"),
    USER_NOT_FOUND("User not found"),
    SESSION_INVALID("Invalid or expired session"),
    SESSION_CREATION_FAILED("Failed to create user session"),
    REFRESH_TOKEN_MISSING("Refresh token is missing"),
    NO_ACTIVE_ROLE_FOUND("No active role found"),
    ROLE_ALREADY_EXISTS("Role name already exists"),
    ROLE_NOT_FOUND("Role not found"),
    ROLE_ASSIGNMENT_FAILED("Failed to assign role to user"),
    INVALID_ROLE_NAME("Role name cannot be empty"),
    USER_ALREADY_HAS_ROLE("User already has this role assigned"),
    ROLE_NOT_ACTIVE("Role is not active");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
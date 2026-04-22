package com.auth.demo.enums;

import org.springframework.core.annotation.MergedAnnotations.Search;

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
    ROLE_NOT_ACTIVE("Role is not active"),
    PROJECT_NOT_FOUND("Project not found"),
    PROJECT_NAME_ALREADY_EXISTS("Project name already exists"),
    PROJECT_CREATION_FAILED("Failed to create project"),
    PROJECT_UPDATE_FAILED("Failed to update project"),
    PROJECT_UPDATE_UNAUTHORIZED("You don't have permission to update this project"),
    PROJECT_DELETE_UNAUTHORIZED("You don't have permission to delete this project"),
    SEARCH_NAME_CANNOT_BE_EMPTY("Search name cannot be empty");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
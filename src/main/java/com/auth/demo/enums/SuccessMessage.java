package com.auth.demo.enums;

public enum SuccessMessage {

    USER_CREATED("User account has been successfully created"),
    LOGIN_SUCCESS("Authentication successful"),
    TOKEN_REFRESHED("Access token refreshed successfully"),
    USER_FETCHED("User retrieved successfully"),
    ROLE_CREATED("Role created successfully"),
    ROLE_UPDATED("Role updated successfully"),
    ROLE_DELETED("Role deleted successfully"),
    ROLES_RETRIEVED("Roles retrieved successfully"),
    ROLE_ASSIGNED("Role assigned to user successfully"),
    ROLE_REVOKED("Role revoked successfully"),
    USER_ROLES_RETRIEVED("User roles retrieved successfully"),
    ROLE_ACTIVATED("Role activated successfully"),
    ROLE_DEACTIVATED("Role deactivated successfully"),
    PROJECT_CREATED("Project created successfully"),
    PROJECT_UPDATED("Project updated successfully"),
    PROJECT_DELETED("Project deleted successfully"),
    PROJECT_RETRIEVED("Project retrieved successfully"),
    PROJECTS_RETRIEVED("Projects retrieved successfully"),
    SEARCH_SUCCESSFUL("Search completed successfully");

    private final String message;

    SuccessMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
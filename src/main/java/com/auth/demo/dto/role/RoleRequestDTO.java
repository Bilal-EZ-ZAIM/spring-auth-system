package com.auth.demo.dto.role;

import com.auth.demo.ValidationMessages.ValidationMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleRequestDTO(
        @NotBlank(message = ValidationMessages.ROLE_NAME_REQUIRED) @Size(min = 1, max = 20, message = ValidationMessages.ROLE_NAME_SIZE) String name,

        @Size(max = 255, message = ValidationMessages.ROLE_DESCRIPTION_SIZE) String description) {
}